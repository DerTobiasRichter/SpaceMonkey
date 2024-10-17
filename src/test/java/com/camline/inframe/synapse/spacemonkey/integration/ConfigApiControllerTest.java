package com.camline.inframe.synapse.spacemonkey.integration;
import com.camline.inframe.synapse.spacemonkey.model.service.Config;
import com.camline.inframe.synapse.spacemonkey.api.service.ConfigApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConfigApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConfigApiDelegate configApiDelegate;

    @Test
    void getConfigTest() throws Exception {
        given(configApiDelegate.getConfig()).willReturn(ResponseEntity.ok(new Config()));

        mvc.perform(MockMvcRequestBuilders
                        .get("/config")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}