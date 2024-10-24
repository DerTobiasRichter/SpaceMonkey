package com.camline.inframe.synapse.spacemonkey.domain.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class SynapseConfig {

    @Id
    private String id;

    private String serviceName;
    private String host;
    private Integer port;
    private String description;

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SynapseConfig that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getServiceName(), that.getServiceName()) &&  Objects.equals(getHost(), that.getHost()) && Objects.equals(getPort(), that.getPort()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getServiceName(), getHost(), getPort(), getDescription());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SynapseConfig{");
        sb.append("id='").append(id).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
