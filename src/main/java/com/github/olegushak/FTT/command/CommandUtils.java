package com.github.olegushak.FTT.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {

    public static String getChatId(Update update){
        return update.getMessage().getChatId().toString();
    }

    public static String getText(Update update){
        return update.getMessage().getText();
    }

    public static String getUsername(Update update){return update.getMessage().getFrom().getUserName();}
}
