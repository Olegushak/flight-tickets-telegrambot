package com.github.olegushak.FTT.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start", "Start bot"),
    STOP("/stop", "Stop bot"),
    HELP("/help", "Bot information"),
    FIND_FLIGHT("/find flight", "Find flight"),
    SUBSCRIBE_TICKET("subscribe to ticket", "title"),
    NO_COMMAND("no command", "title"),
    STAT("/stat", "title"),
    SEARCH_AIRPORT("/search airport"," title"),
    SHARE_LOCATION("/share location", "title"),
    UPDATE_LOCALISATIONS("/update_localisations", "title"),
    UPDATE_AIRPORTS("/update_airports", "title");
   // FLIGHT_HISTORY("завершенные перелеты"),
  //  ALL_FLIGHTS("мои перелеты"),
  //  MAIN("главное меню");

    private final String commandName;
    private final String title;
    private

    CommandName(String commandName, String title) {
        this.commandName = commandName;
        this.title = title;
    }

}
