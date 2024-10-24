package com.camline.inframe.synapse.spacemonkey.controller.service;

import com.camline.inframe.synapse.spacemonkey.api.service.ConfigApiDelegate;
import com.camline.inframe.synapse.spacemonkey.domain.service.SynapseConfig;
import com.camline.inframe.synapse.spacemonkey.domain.service.builder.SynapseConfigBuilder;
import com.camline.inframe.synapse.spacemonkey.model.service.CaConfig;
import com.camline.inframe.synapse.spacemonkey.model.service.Config;
import com.camline.inframe.synapse.spacemonkey.model.service.ServerConfig;
import com.camline.inframe.synapse.spacemonkey.model.service.SpaceConnections;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConfigSynapseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    ConfigSynapseServiceImpl synapseService;

    public ConfigController(ConfigSynapseServiceImpl synapseService) {
        this.synapseService = synapseService;
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
        SpaceConnections spaceConnections = new SpaceConnections();

        return Mono.just(spaceConnections);
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

}
