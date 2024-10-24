package com.camline.inframe.synapse.spacemonkey.domain.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Document
public class Connection {

    @Id
    private String id;

    private String inboundTime;

    private String remoteAddress;
    private String remoteHostName;
    private int remotePort;

    private String localAddress;
    private String localPath;
    private String localHttpRequestMethod;

    private MultiValueMap<String, String> localQueryParams;

    public String getId() {
        return id;
    }

    public String getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(OffsetDateTime inboundTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZ");

        this.inboundTime = inboundTime.format(formatter);
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteHostName() {
        return remoteHostName;
    }

    public void setRemoteHostName(String remotehostName) {
        this.remoteHostName = remotehostName;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalHttpRequestMethod(){
        return localHttpRequestMethod;
    }

    public void setLocalHttpRequestMethod(HttpMethod localHttpRequestMethod) {
        this.localHttpRequestMethod = localHttpRequestMethod.name();
    }

    public MultiValueMap<String, String> getLocalQueryParams() {
        return localQueryParams;
    }

    public void setLocalQueryParams(MultiValueMap<String, String> localQueryParams) {
        this.localQueryParams = localQueryParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection that)) return false;
        return getRemotePort() == that.getRemotePort() && Objects.equals(getId(), that.getId()) && Objects.equals(getInboundTime(), that.getInboundTime()) && Objects.equals(getRemoteAddress(), that.getRemoteAddress()) && Objects.equals(getRemoteHostName(), that.getRemoteHostName()) && Objects.equals(getLocalAddress(), that.getLocalAddress()) && Objects.equals(getLocalPath(), that.getLocalPath()) && Objects.equals(getLocalHttpRequestMethod(), that.getLocalHttpRequestMethod()) && Objects.equals(getLocalQueryParams(), that.getLocalQueryParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInboundTime(), getRemoteAddress(), getRemoteHostName(), getRemotePort(), getLocalAddress(), getLocalPath(), getLocalHttpRequestMethod(), getLocalQueryParams());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Connection{");
        sb.append("id='").append(id).append('\'');
        sb.append(", inboundTime='").append(inboundTime).append('\'');
        sb.append(", remoteAddress='").append(remoteAddress).append('\'');
        sb.append(", remoteHostName='").append(remoteHostName).append('\'');
        sb.append(", remotePort=").append(remotePort);
        sb.append(", localAddress='").append(localAddress).append('\'');
        sb.append(", localPath='").append(localPath).append('\'');
        sb.append(", localHttpRequestMethod='").append(localHttpRequestMethod).append('\'');
        sb.append(", localQueryParams=").append(localQueryParams);
        sb.append('}');
        return sb.toString();
    }

}
