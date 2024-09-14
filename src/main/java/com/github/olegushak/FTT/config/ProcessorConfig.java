package com.github.olegushak.FTT.config;

import com.github.olegushak.FTT.processor.BaseProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProcessorConfig {

    @Bean
    public BaseProcessor telegramBotProcessor(List<BaseProcessor> processors) {
        processors.stream().reduce((p1, p2) ->
            p1.setDownstreamProcessor(p2));

        return processors.get(0);
    }
}
