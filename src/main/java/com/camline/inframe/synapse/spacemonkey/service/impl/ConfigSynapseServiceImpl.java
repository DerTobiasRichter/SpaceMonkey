package com.camline.inframe.synapse.spacemonkey.service.impl;

import com.camline.inframe.synapse.spacemonkey.controller.config.Properties;
import com.camline.inframe.synapse.spacemonkey.domain.service.SynapseConfig;
import com.camline.inframe.synapse.spacemonkey.domain.service.builder.SynapseConfigBuilder;
import com.camline.inframe.synapse.spacemonkey.repository.ConfigSynapseRepository;
import com.camline.inframe.synapse.spacemonkey.service.ConfigSynapseService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConfigSynapseServiceImpl implements ConfigSynapseService {

    private static final Logger log = LoggerFactory.getLogger(ConfigSynapseServiceImpl.class);
    private final ConfigSynapseRepository configSynapseRepository;
    private final Properties properties;

    public ConfigSynapseServiceImpl(ConfigSynapseRepository configSynapseRepository, Properties properties) {
        this.configSynapseRepository = configSynapseRepository;
        this.properties=properties;
    }

    @Override
    public Mono<SynapseConfig> initSynapse() {
        final var synapseHome = getDefaultSynapse();
        return this.configSynapseRepository.deleteAll()
                .then(Mono.just(synapseHome)
                        .flatMap(this.configSynapseRepository::save));
    }

    @Override
    public Mono<SynapseConfig> createSynapse(Mono<SynapseConfig> synapseConfigMono) {
        return synapseConfigMono.flatMap(synConfig ->
                configSynapseRepository.count()
                        .flatMap(count -> {
                            if (count == 0) {
                                // if no config is present, save the new one
                                return configSynapseRepository.save(synConfig);
                            } else if (count == 1) {
                                // if exactly one config is present, update it
                                return configSynapseRepository.findAll()
                                        .next()
                                        .flatMap(config -> {
                                            config.setServiceName(synConfig.getServiceName());
                                            config.setHost(synConfig.getHost());
                                            config.setPort(synConfig.getPort());
                                            config.setDescription(synConfig.getDescription());
                                            return configSynapseRepository.save(config);
                                        });
                            } else {
                                log.atError().log("Recognized, more than one synapse config found - validate the controller implementation!");
                                return configSynapseRepository.deleteAll()
                                        .then(configSynapseRepository.save(synConfig));
                            }
                        }));
    }

    @Override
    public Mono<SynapseConfig> restoreDefaultSynapse() {
        final SynapseConfig synapseConfigHome = getDefaultSynapse();
        return updateConfig(Mono.just(synapseConfigHome));
    }

    @Override
    public Mono<SynapseConfig> updateConfig(Mono<SynapseConfig> synapseConfigMono) {
        return createSynapse(synapseConfigMono);
    }
    public Mono<SynapseConfig> getConfig() {
        return configSynapseRepository.findAll().next();
    }

    private @NotNull SynapseConfig getDefaultSynapse() {
        String serviceName = properties.getSynapseDefaultServiceName();
        String host = properties.getSynapseDefaultHost();
        int port = Integer.parseInt(properties.getSynapseDefaultPort());
        String description = properties.getSynapseDefaultDescription();
        log.atWarn().log("Synapse Default restored: \n\t\t\tServiceName:" +
                serviceName + "\n\t\t\tHost:" +
                host + "\n\t\t\tPort: " +
                port + "\n\t\t\tDescription: " +
                description);

        return SynapseConfigBuilder.aSynapseConfig()
                .withServiceName(serviceName)
                .withHost(host)
                .withPort(port)
                .withDescription(description)
                .build();
    }

}
