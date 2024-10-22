package com.camline.inframe.synapse.spacemonkey.integration.repository;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.repository.ConnectionRepository;
import com.camline.inframe.synapse.spacemonkey.utility.ConnectionEntities;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import reactor.test.StepVerifier;


@SpringBootTest
@ActiveProfiles("dev-test")
class ConnectionRepositoryTest {

    @Autowired
    ConnectionRepository connectionRepository;

    @BeforeEach
    void setup() {

        List<Connection> connectionList = new ArrayList<>();
        connectionList.add(ConnectionEntities.getSimpleTestConnection());

        connectionRepository.saveAll(connectionList).blockLast();
    }

    @AfterEach
    void tearDown(){
       connectionRepository.deleteAll().block();
    }


    @Test
    void findAll() {

        Flux<Connection> connections = connectionRepository.findAll().log();

        StepVerifier.create(connections)
                .expectNextCount(1)
                .verifyComplete();
    }
}