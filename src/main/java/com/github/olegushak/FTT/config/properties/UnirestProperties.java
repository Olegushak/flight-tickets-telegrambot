package com.github.olegushak.FTT.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("rapid.api")
@Setter
@Getter // TODO Use @Data instead of @Getter @Setter
public class UnirestProperties {
    private String key;
}
