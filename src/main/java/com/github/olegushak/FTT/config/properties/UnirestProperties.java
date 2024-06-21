package com.github.olegushak.FTT.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "rapid.api")
public class UnirestProperties {
    public String key;
}
