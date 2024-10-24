package com.camline.inframe.synapse.spacemonkey.domain.service.builder;

import com.camline.inframe.synapse.spacemonkey.domain.service.SynapseConfig;

public final class SynapseConfigBuilder {
    private String serviceName;
    private String host;
    private Integer port;
    private String description;

    private SynapseConfigBuilder() {
    }

    public static SynapseConfigBuilder aSynapseConfig() {
        return new SynapseConfigBuilder();
    }

    public SynapseConfigBuilder withServiceName(String serviceName){
        this.serviceName = serviceName;
        return this;
    }

    public SynapseConfigBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public SynapseConfigBuilder withPort(Integer port) {
        this.port = port;
        return this;
    }

    public SynapseConfigBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SynapseConfig build() {
        SynapseConfig synapseConfig = new SynapseConfig();
        synapseConfig.setServiceName(serviceName);
        synapseConfig.setHost(host);
        synapseConfig.setPort(port);
        synapseConfig.setDescription(description);
        return synapseConfig;
    }
}
