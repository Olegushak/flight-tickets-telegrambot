package com.github.olegushak.FTT.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command{

    void execute(Update update);
}
