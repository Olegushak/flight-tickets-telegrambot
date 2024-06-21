package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.config.properties.UnirestProperties;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UnirestProperties.class)
public class UnirestConfig {

    @Autowired
    private UnirestProperties unirestProperties;

    @Value("${rapid.api.host}")
    private String rapidApiHost;

    @Bean
    public UnirestInstance postConstruct() {
        UnirestInstance unirest = Unirest.primaryInstance();
        unirest.config()
                .setDefaultHeader("x-rapidapi-key", unirestProperties.key)
                .addDefaultHeader("x-rapidapi-host", rapidApiHost);
        return unirest;
    }
}
