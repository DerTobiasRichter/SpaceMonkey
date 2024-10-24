package com.camline.inframe.synapse.spacemonkey.integration;

import com.camline.inframe.synapse.spacemonkey.controller.config.util.ServiceResponseMessages;
import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.model.space.*;
import com.camline.inframe.synapse.spacemonkey.controller.config.Properties;
import com.camline.inframe.synapse.spacemonkey.controller.space.CorrectiveActionController;
import com.camline.inframe.synapse.spacemonkey.service.impl.ConnectionServiceImpl;
import com.camline.inframe.synapse.spacemonkey.utility.ConnectionEntities;
import com.camline.inframe.synapse.spacemonkey.utility.CorrectiveActionEntities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@WebFluxTest(CorrectiveActionController.class)
@AutoConfigureWebTestClient
@ActiveProfiles("dev-test")
class CorrectiveActionControllerTest extends SpaceMonkeyTestUtils {

    private final static String CORRECTIVE_ACTION = "/corrective-action";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ConnectionServiceImpl connectionService;

    @MockBean
    Properties properties;

    @BeforeEach
    public void setup() {
        //webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

        Mockito.when(connectionService.setConnection(Mockito.any(Connection.class))).thenReturn(Mono.just(ConnectionEntities.getSimpleTestConnection()));
    }

    @Test
    void getCorrectiveAction() {

        webTestClient.get()
                .uri(SPACE_URL + CORRECTIVE_ACTION)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200))
                .expectBody(ServiceResponce.class).consumeWith(
                        entityExchangeResult -> {
                            ServiceResponce serviceResponce = entityExchangeResult.getResponseBody();

                            Assertions.assertNotNull(serviceResponce);
                            Assertions.assertEquals(ServiceResponseMessages.SERVICE_RESPONSE_ACCEPTED.getMessage(), serviceResponce.getMessage());
                            Assertions.assertEquals(properties.getDurationQuantity(), serviceResponce.getDurrationquantity());
                            Assertions.assertTrue(OffsetDateTime.now().isAfter(serviceResponce.getDatetime()));
                            Assertions.assertTrue(serviceResponce.getDurration() >= 0);
                        }
                );
    }

    @Test
    void postCorrectiveAction(){
        webTestClient.post()
                .uri(SPACE_URL + CORRECTIVE_ACTION)
                .body(Mono.just(CorrectiveActionEntities.getSimpleCaSelectedSamplesData()), CaSelectedSamplesData.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200))
                .expectBody(ServiceResponce.class).consumeWith(
                        entityExchangeResult -> {
                            ServiceResponce serviceResponce = entityExchangeResult.getResponseBody();

                            Assertions.assertNotNull(serviceResponce);
                            Assertions.assertEquals(ServiceResponseMessages.SERVICE_RESPONSE_PROCESSED.getMessage(), serviceResponce.getMessage());
                            Assertions.assertEquals(properties.getDurationQuantity(), serviceResponce.getDurrationquantity());
                            Assertions.assertTrue(OffsetDateTime.now().isAfter(serviceResponce.getDatetime()));
                            Assertions.assertTrue(serviceResponce.getDurration() >= 0);
                        }
                );
    }
}
