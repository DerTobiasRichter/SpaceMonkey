package com.camline.inframe.synapse.spacemonkey.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    public Properties() {
        super();
    }

    @Value("${space-monkey.service.response.duration.quantity}")
    private String durationQuantity;
    @Value("${spring.data.mongodb.host:localhost}")
    private String mongoDbHost;
    @Value("${spring.data.mongodb.port:27017}")
    private String mongoDbPort;
    @Value("${spring.data.mongodb.database:space-monkey}")
    private String mongoDbDatabase;

    @Value("${space-monkey.service.connection.synapseConfig.servicename.default:Default-Synapse-MES-Service}")
    private String synapseDefaultServiceName;
    @Value("${space-monkey.service.connection.synapseConfig.host.default:localhost}")
    private String synapseDefaultHost;
    @Value("${space-monkey.service.connection.synapseConfig.port.default:443}")
    private String synapseDefaultPort;
    @Value("${space-monkey.service.connection.synapseConfig.description.default:Fallback-Synapse-Config}")
    private String synapseDefaultDescription;

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

    public String getSynapseDefaultServiceName() {
        return synapseDefaultServiceName;
    }

    public String getSynapseDefaultHost() {
        return synapseDefaultHost;
    }

    public String getSynapseDefaultPort() {
        return synapseDefaultPort;
    }

    public String getSynapseDefaultDescription() {
        return synapseDefaultDescription;
    }


}