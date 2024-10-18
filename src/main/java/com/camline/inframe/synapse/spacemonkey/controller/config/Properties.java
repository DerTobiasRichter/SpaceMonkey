package com.camline.inframe.synapse.spacemonkey.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    public Properties() {
        super();
    }

    @Value("${spacemonkey.duration.quantity}")
    private String durationQuantity;

    @Value("${spring.data.mongodb.host}")
    private String mongoDbHost;
    @Value("${spring.data.mongodb.port}")
    private String mongoDbPort;
    @Value("${spring.data.mongodb.database}")
    private String mongoDbDatabase;


    public String getDurationQuantity() {
        return durationQuantity;
    }

    public String getMongoDbHost() {
        return mongoDbHost;
    }

    public String getMongoDbPort() {
        return mongoDbPort;
    }

    public String getMongoDbDatabase() {
        return mongoDbDatabase;
    }
}