package com.github.olegushak.FTT.command;

import static com.github.olegushak.FTT.command.CommandName.START;
import static com.github.olegushak.FTT.command.StartCommand.START_MESSAGE;

public class StartCommandNameTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService,telegramUserService);
    }
}
