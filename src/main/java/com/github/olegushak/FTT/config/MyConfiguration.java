package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.config.properties.UnirestProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UnirestProperties.class)
public class MyConfiguration {
}
