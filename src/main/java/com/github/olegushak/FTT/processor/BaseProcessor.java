package com.github.olegushak.FTT.processor;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class BaseProcessor {
    private BaseProcessor downstreamProcessor;

    public abstract void execute(Update update);

    public BaseProcessor setDownstreamProcessor(BaseProcessor processor) {
        downstreamProcessor = processor;
        return downstreamProcessor;
    }
}
