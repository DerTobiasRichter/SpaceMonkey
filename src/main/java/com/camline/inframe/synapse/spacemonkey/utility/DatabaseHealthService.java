package com.camline.inframe.synapse.spacemonkey.utility;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DatabaseHealthService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public DatabaseHealthService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Boolean> isMongoDbAccessible() {
        return reactiveMongoTemplate
                .executeCommand("{ ping: 1 }")
                .map(commandResult -> true)
                .onErrorResume(e -> Mono.just(false));
    }
}