package com.github.olegushak.FTT.command.searchCommand;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface SearchCommand {
    void execute(Update update );
}
