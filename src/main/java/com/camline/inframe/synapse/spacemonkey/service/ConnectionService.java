package com.camline.inframe.synapse.spacemonkey.service;


import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import reactor.core.publisher.Flux;

public interface ConnectionService {

    Flux<Connection> getAllConnections();

}
