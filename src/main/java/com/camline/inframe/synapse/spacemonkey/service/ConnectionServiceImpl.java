package com.camline.inframe.synapse.spacemonkey.service;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import com.camline.inframe.synapse.spacemonkey.repository.ConnectionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
}
