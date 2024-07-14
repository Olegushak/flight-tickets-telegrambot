package com.github.olegushak.FTT.command;

import static com.github.olegushak.FTT.command.CommandName.NO_COMMAND;
import static com.github.olegushak.FTT.command.NoCommand.NO_MESSAGE;

public class NoCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return NO_COMMAND.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }
}
