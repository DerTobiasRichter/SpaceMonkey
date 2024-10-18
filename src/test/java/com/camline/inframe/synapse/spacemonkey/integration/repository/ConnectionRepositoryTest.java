package com.camline.inframe.synapse.spacemonkey.integration.repository;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.repository.ConnectionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

        Connection testConnection = new Connection();


        testConnection.setRemoteAddress("127.0.0.1");
        testConnection.setAddress("127.0.0.1");
        testConnection.setHostName("localhost");
        testConnection.setPort(8080);
        testConnection.setLocalAddress("127.0.0.1");
        testConnection.setPath("/test/mongo/connection");
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("param1", "value1");
        queryParams.add("param2", "value2");
        testConnection.setQueryParams(queryParams);

        List<Connection> connectionList = new ArrayList<>();
        connectionList.add(testConnection);

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