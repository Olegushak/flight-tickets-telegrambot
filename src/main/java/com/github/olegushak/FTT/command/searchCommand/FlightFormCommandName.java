package com.github.olegushak.FTT.command.searchCommand;

import lombok.Getter;

@Getter
public enum FlightFormCommandName {
    FROM("From"),
    TO("To"),
    DEPART("Depart date"),
    RETURN("Return date");

    private final String commandName;


    FlightFormCommandName(String commandName) {
        this.commandName = commandName;
    }
}
