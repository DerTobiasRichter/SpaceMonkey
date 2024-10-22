package com.camline.inframe.synapse.spacemonkey.service.impl;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.repository.ConnectionRepository;
import com.camline.inframe.synapse.spacemonkey.service.ConnectionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    ConnectionRepository connectionRepository;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Flux<Connection> getAllConnections() {
        return this.connectionRepository.findAll();
    }

    @Override
    public Mono<Connection> setConnection(Connection connection) {
        return this.connectionRepository.save(connection).thenReturn(connection);
    }
}
