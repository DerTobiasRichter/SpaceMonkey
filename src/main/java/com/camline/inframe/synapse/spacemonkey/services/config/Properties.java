package com.camline.inframe.synapse.spacemonkey.services.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    public Properties() {
        super();
    }

    @Value("${spacemonkey.duration.quantity}")
    private String durationQuantity;

    public String getDurationQuantity() {
        return durationQuantity;
    }

    public void setDurationQuantity(String durationQuantity) {
        this.durationQuantity = durationQuantity;
    }
}