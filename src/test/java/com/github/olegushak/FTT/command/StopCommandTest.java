package com.github.olegushak.FTT.command;

import static com.github.olegushak.FTT.command.CommandName.STOP;
import static com.github.olegushak.FTT.command.StopCommand.STOP_MESSAGE;

public class StopCommandTest extends AbstractCommandTest{

    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService,telegramUserService);
    }
}
