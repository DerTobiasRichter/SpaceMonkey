package com.camline.inframe.synapse.spacemonkey.domain.config;

public final class SynapseConfigBuilder {
    private String host;
    private Integer port;
    private String description;

    private SynapseConfigBuilder() {
    }

    public static SynapseConfigBuilder aSynapseConfig() {
        return new SynapseConfigBuilder();
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
        synapseConfig.setHost(host);
        synapseConfig.setPort(port);
        synapseConfig.setDescription(description);
        return synapseConfig;
    }
}
