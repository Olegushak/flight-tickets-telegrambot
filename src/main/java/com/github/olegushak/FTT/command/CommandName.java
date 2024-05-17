package com.github.olegushak.FTT.command;

import lombok.Getter;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
   // FIND_FLIGHT("найти билет"),
   // FOLLOW_FLIGHT("отслеживать билет"),
    NO("nocommand"),
    STAT("/stat");
   // FLIGHT_HISTORY("завершенные перелеты"),
  //  ALL_FLIGHTS("мои перелеты"),
  //  MAIN("главное меню");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
