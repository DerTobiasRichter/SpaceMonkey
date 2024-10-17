package com.camline.inframe.synapse.spacemonkey.services;

import com.camline.inframe.synapse.spacemonkey.api.service.ConfigApiDelegate;
import com.camline.inframe.synapse.spacemonkey.model.service.CaConfig;
import com.camline.inframe.synapse.spacemonkey.model.service.Config;
import com.camline.inframe.synapse.spacemonkey.model.service.ServerConfig;
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

    @Override
    @GetMapping("/info")
    public Mono<ResponseEntity<Config>> getConfig(ServerWebExchange exchange) {

        CaConfig spaceCaConfig = new CaConfig();
        spaceCaConfig.setName("hupe.blau.HOLD");
        spaceCaConfig.setDescription("Hupe is drunk and must be set to stage: HOLD");

        List<CaConfig> caList = new ArrayList<>();
        caList.add(spaceCaConfig);

        ServerConfig synapseConfig = new ServerConfig();
        synapseConfig.setServiceName("Synapse Config");
        synapseConfig.setUrl("http://localhost");
        synapseConfig.setPort(8090);

        ServerConfig spaceConfig = new ServerConfig();
        spaceConfig.setServiceName("Space Config");
        spaceConfig.setUrl("http://localhost");
        spaceConfig.setPort(8099);

        Config config = new Config();
        config.setSpaceConfig(spaceConfig);
        config.setSynapseConfig(synapseConfig);
        config.setSpaceCAConfig(caList);

        return Mono.just(new ResponseEntity<>(config,HttpStatus.OK)).log();
    }
}
