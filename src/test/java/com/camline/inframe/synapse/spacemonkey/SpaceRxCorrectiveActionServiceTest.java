package com.camline.inframe.synapse.spacemonkey;

import com.camline.inframe.synapse.spacemonkey.model.space.*;
import com.camline.inframe.synapse.spacemonkey.services.space.CorrectiveActionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

@WebFluxTest(CorrectiveActionService.class)
public class SpaceRxCorrectiveActionServiceTest {

    private final static String TRACKING_UNIT_NAME = "spacemonkey";
    private final static  Long SAMPLE_ID = 12345L;
    private final static String IMMEDIATE_ATTR = "immediate";

    private CaSelectedSamplesData validCaSelectedSamplesData = null;

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CorrectiveActionService correctiveActionService;

    @Mock
    private MockServerWebExchange mockedExchange;

    @BeforeEach
    public void setup() {

        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

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

        OffsetDateTime startTime = OffsetDateTime.now();

        MockitoAnnotations.openMocks(this);
        MockServerHttpRequest request = MockServerHttpRequest.get("/space/correctiveaction").build();
        this.mockedExchange = MockServerWebExchange.from(request);
        given(correctiveActionService.getCorrectiveAction(mockedExchange)).willReturn(Mono.empty());

        webTestClient.get()
                .uri("/space/correctiveaction")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(418);

    }
}
