package com.github.olegushak.FTT.config;

import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnirestConfig {

    @Value("${rapid.api.key}")
    public String rapidApiKey;

    @Value("${rapid.api.host}")
    public String rapidApiHost;

    @Bean
    public UnirestInstance postConstruct() {
        UnirestInstance unirest = Unirest.primaryInstance();
        unirest.config()
                .setDefaultHeader("x-rapidapi-key", rapidApiKey)
                .addDefaultHeader("x-rapidapi-host", rapidApiHost);
        return unirest;
    }
}
