package com.camline.inframe.synapse.spacemonkey.domain.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Document
public class Connection {

    @Id
    String id;

    String remoteAddress;
    String address;
    String hostName;
    int port;

    String  localAddress;
    String path;
    MultiValueMap<String, String> queryParams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultiValueMap<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(MultiValueMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection that)) return false;
        return getPort() == that.getPort() && Objects.equals(getId(), that.getId()) && Objects.equals(getRemoteAddress(), that.getRemoteAddress()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getHostName(), that.getHostName()) && Objects.equals(getLocalAddress(), that.getLocalAddress()) && Objects.equals(getPath(), that.getPath()) && Objects.equals(getQueryParams(), that.getQueryParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRemoteAddress(), getAddress(), getHostName(), getPort(), getLocalAddress(), getPath(), getQueryParams());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Connection{");
        sb.append("id='").append(id).append('\'');
        sb.append(", remoteAddress='").append(remoteAddress).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", hostName='").append(hostName).append('\'');
        sb.append(", port=").append(port);
        sb.append(", localAddress='").append(localAddress).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", queryParams=").append(queryParams);
        sb.append('}');
        return sb.toString();
    }
}