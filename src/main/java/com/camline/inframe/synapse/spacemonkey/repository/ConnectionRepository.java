package com.camline.inframe.synapse.spacemonkey.repository;

import com.camline.inframe.synapse.spacemonkey.domain.service.Connection;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ConnectionRepository extends ReactiveMongoRepository<Connection, String> {
}
