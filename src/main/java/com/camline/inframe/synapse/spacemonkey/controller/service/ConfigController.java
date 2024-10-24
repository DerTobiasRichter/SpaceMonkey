package com.camline.inframe.synapse.spacemonkey.controller.service;

import com.camline.inframe.synapse.spacemonkey.api.service.ConfigApiDelegate;
import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.domain.service.SynapseConfig;
import com.camline.inframe.synapse.spacemonkey.domain.service.builder.SynapseConfigBuilder;
import com.camline.inframe.synapse.spacemonkey.model.service.*;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConfigSynapseServiceImpl;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConnectionServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller handling config tasks in the SpaceMonkey Service.
 *  <p>
 *      Info: RequestMappings are for Unit Tests, not necessarily for the API mappings.
 *      Don't get confused!
 *  <p/>
 */

@RestController
@RequestMapping("/config")
public class ConfigController implements ConfigApiDelegate {

    private final ConfigSynapseServiceImpl synapseService;
    private final ConnectionServiceImpl connectionService;

    public ConfigController(ConfigSynapseServiceImpl synapseService, ConnectionServiceImpl connectionService) {
        this.synapseService = synapseService;
        this.connectionService = connectionService;
    }

    @Override
    @GetMapping("/info")
    public Mono<ResponseEntity<Config>> getConfig(final ServerWebExchange exchange) {

        Mono<SpaceConnections> spaceConnectionMono = getSpaceConnections();
        Mono<ServerConfig> synapseConfigMono = getSynapseConfig();
        Mono<List<CaConfig>> caConfigMono = getCaConfig();

        return Mono.zip(
                spaceConnectionMono,synapseConfigMono,caConfigMono).flatMap(
                        t3 -> {
                            Config config = new Config();
                            config.setSpaceConfig(t3.getT1());
                            config.setSynapseConfig(t3.getT2());
                            config.setSpaceCAConfig(t3.getT3());

                            return Mono.just(new ResponseEntity<>(config, HttpStatus.OK));

                        });
    }

    @Override
    @GetMapping("/synapse")
    public Mono<ResponseEntity<ServerConfig>> getSynapseConfig(ServerWebExchange exchange) {
        return getSynapseConfig()
                .map(serverConfig -> new ResponseEntity<>(serverConfig, HttpStatus.OK))
                .log();
    }

    @Override
    @PostMapping("/synapse")
    public Mono<ResponseEntity<ServerConfig>> postSynapseConfig(Mono<ServerConfig> serverConfig, ServerWebExchange exchange) {
        return setSynapseConfig(serverConfig)
                .map( outServerConfig -> new ResponseEntity<>(outServerConfig, HttpStatus.OK)).log();
    }

    @Override
    @DeleteMapping("/synapse")
    public Mono<ResponseEntity<ServerConfig>> deleteSynapseConfig(ServerWebExchange exchange) {
        return synapseService.restoreDefaultSynapse()
                .map(this::mapToServerConfig)
                .map(serverConfig -> new ResponseEntity<>(serverConfig, HttpStatus.OK));
    }

    /* Controller Workers */

    private Mono<ServerConfig> getSynapseConfig(){
        return synapseService.getConfig().map(this::mapToServerConfig);
    }

    private Mono<ServerConfig> setSynapseConfig(Mono<ServerConfig> serverConfigMono){
        return serverConfigMono.flatMap(inEntity ->
                synapseService.updateConfig(Mono.just(mapToSynapseConfig(inEntity)))
                        .map(this::mapToServerConfig)
        ).log();
    }

    private Mono<SpaceConnections> getSpaceConnections() {
        return connectionService.getAllConnections()
                .map(this::mapToSpaceConnection).collectList()
                .map(spaceConnectionsList -> {
                    SpaceConnections spaceConnections = new SpaceConnections();
                    spaceConnections.setConnections(spaceConnectionsList);
                    return spaceConnections;
                });
    }

    private Mono<List<CaConfig>> getCaConfig() {
        CaConfig spaceCaConfig = new CaConfig();
        spaceCaConfig.setName("hupe.blau.HOLD");
        spaceCaConfig.setDescription("Hupe is drunk and must be set to stage: HOLD");

        List<CaConfig> caList = new ArrayList<>();
        caList.add(spaceCaConfig);

        return Mono.just(caList);
    }

    /* Mapping Helpers */

    private ServerConfig mapToServerConfig(SynapseConfig synapseConfig) {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServiceName(synapseConfig.getServiceName());
        serverConfig.setHost(synapseConfig.getHost());
        serverConfig.setPort(synapseConfig.getPort());
        serverConfig.setDescription(synapseConfig.getDescription());

        return serverConfig;
    }

    private SynapseConfig mapToSynapseConfig(ServerConfig serverConfig) {
        return SynapseConfigBuilder.aSynapseConfig()
                .withServiceName(serverConfig.getServiceName())
                .withHost(serverConfig.getHost())
                .withPort(serverConfig.getPort())
                .withDescription(serverConfig.getDescription())
                .build();
    }

    private SpaceConnection mapToSpaceConnection(Connection connection) {

        final var spaceConnection = getSpaceConnection(connection);

        MultiValueMap<String, String> connectionQueryParam = connection.getLocalQueryParams();
        if(connectionQueryParam != null){
            for (String key : connectionQueryParam.keySet()){
                QueryParam queryParam = new QueryParam();
                queryParam.setKey(key);
                queryParam.setValue(connectionQueryParam.getFirst(key));
                spaceConnection.addLocalQueryParamsItem(queryParam);
            }
        }

        return spaceConnection;
    }

    private static @NotNull SpaceConnection getSpaceConnection(Connection connection) {
        SpaceConnection spaceConnection = new SpaceConnection();
        spaceConnection.setInboundTime(connection.getInboundTime());
        spaceConnection.setRemoteAddress(connection.getRemoteAddress());
        spaceConnection.setRemoteHostName(connection.getRemoteHostName());
        spaceConnection.setRemotePort(connection.getRemotePort());

        spaceConnection.setLocalAddress(connection.getLocalAddress());
        spaceConnection.setLocalPath(connection.getLocalPath());
        spaceConnection.setLocalHttpRequestMethod(connection.getLocalHttpRequestMethod());
        return spaceConnection;
    }

}
