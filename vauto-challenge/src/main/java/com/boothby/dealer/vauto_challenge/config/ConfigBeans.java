package com.boothby.dealer.vauto_challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {
    
    @Bean
    public AppConfig appConfig(@Value("${api.base.url}") String apiBaseUrl) {
        AppConfig appConfig = new AppConfig();
        appConfig.setApiBaseUrl(apiBaseUrl);
        return appConfig;
    }
}
