package com.example.servicecommandes.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
@RefreshScope
public class ApplicationPropertiesConfiguration {

    private int limitDeCommands;
    public int getLimitDeCommands() {
        return limitDeCommands;
    }
    public void setLimitDeCommands(int limitDeCommands) {
        this.limitDeCommands = limitDeCommands;
    }
}
