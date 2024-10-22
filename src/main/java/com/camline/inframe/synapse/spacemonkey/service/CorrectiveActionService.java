package com.camline.inframe.synapse.spacemonkey.service;

import com.camline.inframe.synapse.spacemonkey.model.space.CaContext;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CorrectiveActionService {

    Flux<CaSelectedSamplesData> setSelectedSamplesData(Mono<CaSelectedSamplesData> caSelectedSamplesDataMono);

    Flux<CaSelectedSamplesData> setSelectedSamplesData(Flux<CaSelectedSamplesData> caSelectedSamplesDataFlux);

    Flux<CaSelectedSamplesData> getAllSelectedSamplesData();

    Flux<CaSelectedSamplesData> getSelectedSamplesData(Mono<CaContext> caContext);

}
