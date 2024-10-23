package com.camline.inframe.synapse.spacemonkey.service;

import com.camline.inframe.synapse.spacemonkey.domain.config.SynapseConfig;
import reactor.core.publisher.Mono;

public interface ConfigSynapseService {

    Mono<SynapseConfig> initSynapse();

    Mono<SynapseConfig> createSynapse(Mono<SynapseConfig> synapse);

    Mono<SynapseConfig> restoreDefaultSynapse();

    Mono<SynapseConfig> updateConfig(Mono<SynapseConfig> synapse);

}
