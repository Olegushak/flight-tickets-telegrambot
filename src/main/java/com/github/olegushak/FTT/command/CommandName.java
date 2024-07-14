package com.github.olegushak.FTT.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    FIND_FLIGHT("find ticket"),
    SUBSCRIBE_TICKET("subscribe to ticket"),
    NO_COMMAND("no command"),
    STAT("/stat"),
    SHARE_LOCATION("/share location"),
    UPDATE_LOCALISATIONS("/update_localisations"),
    UPDATE_AIRPORTS("/update_airports");
   // FLIGHT_HISTORY("завершенные перелеты"),
  //  ALL_FLIGHTS("мои перелеты"),
  //  MAIN("главное меню");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
