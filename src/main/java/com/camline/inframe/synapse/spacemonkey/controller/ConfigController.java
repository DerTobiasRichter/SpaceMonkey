package com.camline.inframe.synapse.spacemonkey.controller;

import com.camline.inframe.synapse.spacemonkey.api.service.ConfigApiDelegate;
import com.camline.inframe.synapse.spacemonkey.domain.config.SynapseConfig;
import com.camline.inframe.synapse.spacemonkey.domain.config.SynapseConfigBuilder;
import com.camline.inframe.synapse.spacemonkey.model.service.CaConfig;
import com.camline.inframe.synapse.spacemonkey.model.service.Config;
import com.camline.inframe.synapse.spacemonkey.model.service.ServerConfig;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConfigSynapseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller handling corrective actions in the SpaceMonkey Config API.
 *
 * Info: RequestMappings are for Unit Tests not necessarily for the API mappings
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

        Mono<ServerConfig> spaceConfigMono = getSpaceConfig();
        Mono<ServerConfig> synapseConfigMono = getSynapseConfig();
        Mono<List<CaConfig>> caConfigMono = getCaConfig();

        return Mono.zip(
                spaceConfigMono,synapseConfigMono,caConfigMono).flatMap(
                        t3 -> {
                            Config config = new Config();
                            config.setSpaceConfig(t3.getT1());
                            config.setSynapseConfig(t3.getT2());
                            config.setSpaceCAConfig(t3.getT3());

                            return Mono.just(new ResponseEntity<>(config, HttpStatus.OK));

                        });
    }

    @Override
    //@GetMapping("/synapse")
    public Mono<ResponseEntity<ServerConfig>> getSynapseConfig(ServerWebExchange exchange) {
        return getSynapseConfig()
                .map(serverConfig -> new ResponseEntity<>(serverConfig, HttpStatus.OK))
                .log();
    }

    @Override
    public Mono<ResponseEntity<ServerConfig>> postSynapseConfig(Mono<ServerConfig> serverConfig, ServerWebExchange exchange) {
        return setSynapseConfig(serverConfig)
                .map( outServerConfig -> new ResponseEntity<>(outServerConfig, HttpStatus.OK)).log();
    }

    @Override
    public Mono<ResponseEntity<ServerConfig>> deleteSynapseConfig(ServerWebExchange exchange) {
        return ConfigApiDelegate.super.deleteSynapseConfig(exchange);
    }

    private Mono<ServerConfig> getSynapseConfig(){

        return synapseService.getConfig().map(entity -> {
            ServerConfig serverConfig = new ServerConfig();
            serverConfig.setServiceName(entity.getServiceName());
            serverConfig.setHost(entity.getHost());
            serverConfig.setPort(entity.getPort());
            serverConfig.setDescription(entity.getDescription());

            return serverConfig;
        });
    }

    private Mono<ServerConfig> setSynapseConfig(Mono<ServerConfig> serverConfigMono){
        return serverConfigMono.flatMap(inEntity -> {
            SynapseConfig synapseConfig = SynapseConfigBuilder.aSynapseConfig()
                    .withServiceName(inEntity.getServiceName())
                    .withHost(inEntity.getHost())
                    .withPort(inEntity.getPort())
                    .withDescription(inEntity.getDescription())
                    .build();

            return synapseService.updateConfig(Mono.just(synapseConfig))
                    .map(outEntity -> {
                        ServerConfig outServerConfig = new ServerConfig();
                        outServerConfig.setServiceName(outEntity.getServiceName());
                        outServerConfig.setHost(outEntity.getHost());
                        outServerConfig.setPort(outEntity.getPort());
                        outServerConfig.setDescription(outEntity.getDescription());

                        return outServerConfig;
                    });
        });
    }

    private Mono<ServerConfig> getSpaceConfig() {
        ServerConfig synapseConfig = new ServerConfig();
        synapseConfig.setServiceName("Space Config Config");
        synapseConfig.setHost("http://localhost");
        synapseConfig.setPort(8090);
        synapseConfig.setDescription("Test Config String");

        return Mono.just(synapseConfig);
    }

    private Mono<List<CaConfig>> getCaConfig() {
        CaConfig spaceCaConfig = new CaConfig();
        spaceCaConfig.setName("hupe.blau.HOLD");
        spaceCaConfig.setDescription("Hupe is drunk and must be set to stage: HOLD");

        List<CaConfig> caList = new ArrayList<>();
        caList.add(spaceCaConfig);

        return Mono.just(caList);
    }
}
