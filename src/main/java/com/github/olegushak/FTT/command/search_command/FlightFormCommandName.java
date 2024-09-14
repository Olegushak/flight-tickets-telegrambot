package com.github.olegushak.FTT.command.search_command;

import lombok.Getter;

@Getter
public enum FlightFormCommandName {
    FROM("/from", "From"),
    TO("/to", "To"),
    DEPART("/depart date", "Depart date"),
    RETURN("/return date","Return date"),
    SEARCH("/search","Search");

    private final String commandName;
    private final String title;


    FlightFormCommandName(String commandName, String title) {
        this.commandName = commandName;
        this.title = title;
    }
}
