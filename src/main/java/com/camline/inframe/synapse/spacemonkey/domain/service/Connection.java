package com.camline.inframe.synapse.spacemonkey.domain.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
    private String remotehostName;
    private int remotePort;

    private String localAddress;
    private String localPath;
    private MultiValueMap<String, String> localQueryParams;

    public String getId() {
        return id;
    }

    public String getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(OffsetDateTime inboundTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS ZZZ");
        String timeString = inboundTime.format(formatter);

        this.inboundTime = timeString;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemotehostName() {
        return remotehostName;
    }

    public void setRemoteHostName(String remotehostName) {
        this.remotehostName = remotehostName;
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
        return getRemotePort() == that.getRemotePort() && Objects.equals(getId(), that.getId()) && Objects.equals(getInboundTime(), that.getInboundTime()) && Objects.equals(getRemoteAddress(), that.getRemoteAddress()) && Objects.equals(getRemotehostName(), that.getRemotehostName()) && Objects.equals(getLocalAddress(), that.getLocalAddress()) && Objects.equals(getLocalPath(), that.getLocalPath()) && Objects.equals(getLocalQueryParams(), that.getLocalQueryParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInboundTime(), getRemoteAddress(), getRemotehostName(), getRemotePort(), getLocalAddress(), getLocalPath(), getLocalQueryParams());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Connection{");
        sb.append("id='").append(id).append('\'');
        sb.append(", inboundTime='").append(inboundTime).append('\'');
        sb.append(", remoteAddress='").append(remoteAddress).append('\'');
        sb.append(", remotehostName='").append(remotehostName).append('\'');
        sb.append(", remotePort=").append(remotePort);
        sb.append(", localAddress='").append(localAddress).append('\'');
        sb.append(", localPath='").append(localPath).append('\'');
        sb.append(", localQueryParams=").append(localQueryParams);
        sb.append('}');
        return sb.toString();
    }
}
