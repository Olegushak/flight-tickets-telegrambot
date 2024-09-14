package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    @Bean
    public CacheStore<FlightRequestArgs> flightRequestCache() {
        return new CacheStore<>(2, TimeUnit.HOURS);
    }


    @Bean
    public CacheStore<FlightReviewEntity> flightReviewCache(){
        return new CacheStore<>(2,TimeUnit.HOURS);
    }
}
