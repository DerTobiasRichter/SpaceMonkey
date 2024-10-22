package com.camline.inframe.synapse.spacemonkey.controller.config.util;

public enum ServiceResponseMessages {

    SERVICE_RESPONSE_ACCEPTED("Service Response: Accepted"),
    SERVICE_RESPONSE_PROCESSED("Service Response: Processed"),
    SERVICE_RESPONSE_REJECTED("Service Response: Rejected"),
    SERVICE_RESPONSE_ERROR("Service Response: Error");

    private final String message;

    ServiceResponseMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
