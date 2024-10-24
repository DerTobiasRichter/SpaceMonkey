package com.camline.inframe.synapse.spacemonkey.integration;
import com.camline.inframe.synapse.spacemonkey.model.space.ServiceResponce;
import com.camline.inframe.synapse.spacemonkey.controller.service.ConfigController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ConfigController.class)
@AutoConfigureWebTestClient
class ConfigApiControllerTest extends SpaceMonkeyTestUtils {


    private final static String INFO = "/info";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getConfigTest() {

        webTestClient.get()
                .uri(CONFIG_URL + INFO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ServiceResponce.class).consumeWith(
                        entityExchangeResult -> {});

    }
}