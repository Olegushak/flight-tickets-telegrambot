package com.github.olegushak.FTT.command.searchCommand;

import lombok.Getter;

@Getter
public enum FlightFormCommandName {
    FROM("/from", "From"),
    TO("/to", "To"),
    DEPART("/depart date", "Depart date"),
    RETURN("/return date","Return date");

    private final String commandName;
    private final String title;


    FlightFormCommandName(String commandName, String title) {
        this.commandName = commandName;
        this.title = title;
    }
}
