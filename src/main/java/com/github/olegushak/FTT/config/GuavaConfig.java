package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    @Bean
    public CacheStore<FlightRequestArgs> employeeCache() {
        return new CacheStore<>(2, TimeUnit.HOURS);
    }
}
