package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.config.properties.UnirestProperties;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnirestConfig {

    private final UnirestProperties unirestProperties;

    @Value("${rapid.api.host}")
    private String rapidApiHost;

    @Autowired
    public UnirestConfig(UnirestProperties unirestProperties) {
        this.unirestProperties = unirestProperties;
    }

    @Bean
    public UnirestInstance postConstruct() {
        UnirestInstance unirest = Unirest.primaryInstance();
        unirest.config()
                .setDefaultHeader("x-rapidapi-key", unirestProperties.getKey())
                .addDefaultHeader("x-rapidapi-host", rapidApiHost);
        return unirest;
    }
}
