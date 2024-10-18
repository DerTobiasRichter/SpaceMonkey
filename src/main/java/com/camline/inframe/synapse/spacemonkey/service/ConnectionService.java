package com.camline.inframe.synapse.spacemonkey.service;


import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConnectionService {

    Flux<Connection> getAllConnections();

    Mono<Connection> setConnection(Connection connection);

}
