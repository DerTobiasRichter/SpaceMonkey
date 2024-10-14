package com.camline.inframe.synapse.spacemonkey.controller;

import com.camline.inframe.synapse.spacemonkey.api.ConfigApiDelegate;
import com.camline.inframe.synapse.spacemonkey.model.CaConfig;
import com.camline.inframe.synapse.spacemonkey.model.Config;
import com.camline.inframe.synapse.spacemonkey.model.ServerConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigController implements ConfigApiDelegate {

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<Config> getConfig() {
        Config config = new Config();

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

        config.setSpaceConfig(spaceConfig);
        config.setSynapseConfig(synapseConfig);
        config.setSpaceCAConfig(caList);

        return ResponseEntity.ok(config);
    }
}
