package com.camline.inframe.synapse.spacemonkey.integration.repository;


import com.camline.inframe.synapse.spacemonkey.model.space.CaSelectedSamplesData;
import com.camline.inframe.synapse.spacemonkey.repository.CorrectiveActionRepository;
import com.camline.inframe.synapse.spacemonkey.utility.CorrectiveActionEntities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev-test")
class CorrectiveActionRepositoryTest {

    @Autowired
    CorrectiveActionRepository correctiveActionRepository;

    @BeforeEach
    void setup() {

        List<CaSelectedSamplesData> caSelectedSamplesDataList  = new ArrayList<>();
        caSelectedSamplesDataList.add(CorrectiveActionEntities.getSimpleCaSelectedSamplesData());

        correctiveActionRepository.saveAll(caSelectedSamplesDataList).log().blockLast();
    }

    @AfterEach
    void tearDown() {
        correctiveActionRepository.deleteAll().log().block();
    }

    @Test
    void findByCaContextLdsId() {
        Integer caContextLdsId = CorrectiveActionEntities.getSimpleCaSelectedSamplesData().getCaContext().getLdsId();
        Flux<CaSelectedSamplesData> result = correctiveActionRepository.findByCaContext_ldsId(caContextLdsId).log();
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findAll(){
        Flux<CaSelectedSamplesData> result = correctiveActionRepository.findAll().log();
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

}