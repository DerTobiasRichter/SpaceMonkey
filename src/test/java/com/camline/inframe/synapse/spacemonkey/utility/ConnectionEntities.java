package com.camline.inframe.synapse.spacemonkey.utility;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.OffsetDateTime;

public class ConnectionEntities {

    public static Connection getSimpleTestConnection(){

        Connection testConnection = new Connection();

        testConnection.setInboundTime(OffsetDateTime.now());
        testConnection.setRemoteAddress("127.0.0.1");
        testConnection.setRemoteAddress("127.0.0.1");
        testConnection.setRemoteHostName("localhost");
        testConnection.setRemotePort(8080);
        testConnection.setLocalAddress("127.0.0.1");
        testConnection.setLocalPath("/test/mongo/connection");
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("param1", "value1");
        queryParams.add("param2", "value2");
        testConnection.setLocalQueryParams(queryParams);

        return testConnection;
    }
}
