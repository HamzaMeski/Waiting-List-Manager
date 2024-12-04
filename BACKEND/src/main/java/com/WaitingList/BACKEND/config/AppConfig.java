package com.WaitingList.BACKEND.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "default")
public class AppConfig {
    private int maxCapacity;
    private String defaultAlgo;

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public String getDefaultAlgo() {
        return this.defaultAlgo;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setDefaultAlgo(String defaultAlgo) {
        this.defaultAlgo = defaultAlgo;
    }
}
