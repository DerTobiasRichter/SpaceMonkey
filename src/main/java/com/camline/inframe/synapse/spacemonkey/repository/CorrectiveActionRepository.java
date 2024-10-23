package com.camline.inframe.synapse.spacemonkey.repository;

import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CorrectiveActionRepository  extends ReactiveMongoRepository<CaSelectedSamplesData, String> {

    Flux<CaSelectedSamplesData> findByCaContext_ldsId(Integer caContextLdsId);

}
