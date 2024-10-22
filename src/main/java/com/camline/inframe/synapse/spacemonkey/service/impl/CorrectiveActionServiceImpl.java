package com.camline.inframe.synapse.spacemonkey.service.impl;

import com.camline.inframe.synapse.spacemonkey.model.space.CaContext;
import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.repository.CorrectiveActionRepository;
import com.camline.inframe.synapse.spacemonkey.service.CorrectiveActionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorrectiveActionServiceImpl implements CorrectiveActionService {

    private final CorrectiveActionRepository correctiveActionRepository;

    public CorrectiveActionServiceImpl(CorrectiveActionRepository correctiveActionRepository) {
        this.correctiveActionRepository = correctiveActionRepository;
    }

    @Override
    public Mono<CaSelectedSamplesData> setSelectedSamplesData(Mono<CaSelectedSamplesData> caSelectedSamplesDataMono) {
        return caSelectedSamplesDataMono
                .flatMap(this.correctiveActionRepository::save);
    }

    @Override
    public Flux<CaSelectedSamplesData> setSelectedSamplesData(Flux<CaSelectedSamplesData> caSelectedSamplesDataFlux) {
        return caSelectedSamplesDataFlux
                .flatMap(this.correctiveActionRepository::save);
    }

    @Override
    public Flux<CaSelectedSamplesData> getAllSelectedSamplesData() {
        return this.correctiveActionRepository.findAll();
    }

    @Override
    public Flux<CaSelectedSamplesData> getSelectedSamplesData(Mono<CaContext> caContextMono) {
        return caContextMono
                .flatMapMany(
                        caContext -> this.correctiveActionRepository.findByCaContext_ldsId(caContext.getLdsId()));
    }

}
