package com.camline.inframe.synapse.spacemonkey.repository;

import com.camline.inframe.synapse.spacemonkey.domain.service.SynapseConfig;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ConfigSynapseRepository extends ReactiveMongoRepository<SynapseConfig,String> {
}
