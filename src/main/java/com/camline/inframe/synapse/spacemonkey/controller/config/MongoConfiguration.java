package com.camline.inframe.synapse.spacemonkey.controller.config;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.camline.inframe.synapse.spacemonkey.repository")
public class MongoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MongoConfiguration.class);
    private final Properties properties;

    public MongoConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        log.atInfo().log("Bean: ReactiveMongoTemplate try to connect to MongoDB: " + buildConnetionstring());
        return new ReactiveMongoTemplate(MongoClients.create(new ConnectionString(buildConnetionstring())), properties.getMongoDbDatabase());
    }

    private String buildConnetionstring(){
        StringBuilder connetionString = new StringBuilder();
        connetionString.append("mongodb://")
                .append(properties.getMongoDbHost()).append(":")
                .append(properties.getMongoDbPort()).append("/")
                .append(properties.getMongoDbDatabase());

        return connetionString.toString();
    }
}