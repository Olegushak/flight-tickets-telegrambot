package com.github.olegushak.FTT.command;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

import static com.github.olegushak.FTT.command.CommandName.HELP;
import static com.github.olegushak.FTT.command.CommandName.START;
import static com.github.olegushak.FTT.command.CommandName.STOP;
import static com.github.olegushak.FTT.command.CommandName.UPDATE_AIRPORTS;
import static com.github.olegushak.FTT.command.CommandName.UPDATE_LOCALISATIONS;


public class MenuCommand {

    private static final List<BotCommand> MENU_COMMANDS = List.of(
            new BotCommand(START.getCommandName(), "Run bot"),
            new BotCommand(STOP.getCommandName(), "Stop bot"),
            new BotCommand(HELP.getCommandName(), "Information"),
            new BotCommand(UPDATE_LOCALISATIONS.getCommandName(), "Update localisations"),
            new BotCommand(UPDATE_AIRPORTS.getCommandName(), "Update airports")
    );

    public static List<BotCommand> getMenuCommands(){
        return MENU_COMMANDS;
    }
}
