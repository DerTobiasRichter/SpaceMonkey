package com.camline.inframe.synapse.spacemonkey.integration;

import com.camline.inframe.synapse.spacemonkey.model.space.*;
import com.camline.inframe.synapse.spacemonkey.services.config.Properties;
import com.camline.inframe.synapse.spacemonkey.services.space.CorrectiveActionController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

@WebFluxTest(CorrectiveActionController.class)
@AutoConfigureWebTestClient
class CorrectiveActionControllerTest {

    private final static String BASE_URL = "/fix/space/corrective-action";

    private final static String TRACKING_UNIT_NAME = "spacemonkey";
    private final static  Long SAMPLE_ID = 12345L;
    private final static String IMMEDIATE_ATTR = "immediate";

    private CaSelectedSamplesData validCaSelectedSamplesData = null;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    Properties properties;

    @BeforeEach
    public void setup() {

        //webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

        validCaSelectedSamplesData = new CaSelectedSamplesData();
        CaWithSamples sampleCAs = new CaWithSamples();
        validCaSelectedSamplesData.getCaSamples().add(sampleCAs);

        final Sample sample = new Sample();
        sample.setSampleId(SAMPLE_ID);
        final List<SampleKey> extSampleKeys = new ArrayList<>();
        final SampleKey waferId = new SampleKey();
        waferId.setKeyName("WaferId");
        waferId.setKeyValue(TRACKING_UNIT_NAME);
        extSampleKeys.add(waferId);
        sample.setSampleExtractorKeys(extSampleKeys);
        sampleCAs.getSamples().add(sample);

        sample.setSampleExtractorKeys(extSampleKeys);

        final CorrectiveAction ca = new CorrectiveAction();
        ca.setCaName("ACT.UNIT:HOLD");
        final List<String> attributes = new ArrayList<>();
        attributes.add("ACT.UNIT:HOLD");
        attributes.add("ACT.UNIT:HOLD#reason=Hold Reason 1");
        attributes.add("ACT.UNIT:HOLD#code=Split Hold");
        attributes.add(IMMEDIATE_ATTR);
        ca.setCaAttributes(attributes);

        sampleCAs.setCorrectiveAction(ca);
    }

    @Test
    void getCorrectiveActionService() {
//        webTestClient.get()
//                .uri(BASE_URL)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatusCode.valueOf(418))
//                .expectBody();

        EntityExchangeResult<ServiceResponce> responce = webTestClient.get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(418))
                .expectBody(ServiceResponce.class)
                .returnResult();

        ServiceResponce serviceResponce = responce.getResponseBody();

        Assertions.assertEquals("I'm a Tea Pot. ", serviceResponce.getMessage());
//        Assertions.assertEquals(properties.getDurationQuantity(), serviceResponce.getDurrationquantity());
//        Assertions.assertTrue(OffsetDateTime.now().isAfter(serviceResponce.getDatetime()));
//        Assertions.assertTrue(serviceResponce.getDurration() >= 0);
    }
}