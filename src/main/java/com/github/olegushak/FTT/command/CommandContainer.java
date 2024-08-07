package com.github.olegushak.FTT.command;
import com.github.olegushak.FTT.client.AirportsClient;
import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.client.LocalisationClient;
import com.github.olegushak.FTT.command.searchCommand.FindFlightCommand;
import com.github.olegushak.FTT.command.searchCommand.ParameterRequestCommand;
import com.github.olegushak.FTT.command.searchCommand.ParameterHandleCommand;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.LocalisationService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.github.olegushak.FTT.command.CommandName.*;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.DEPART;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.FROM;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.RETURN;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.TO;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            FlightsClient flightsClient, FlightService flightService, LocalisationService localisationService,
                            LocalisationClient localisationClient, AirportService airportService, AirportsClient airportsClient) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService,telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService,telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO_COMMAND.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService,telegramUserService))
                .put(SUBSCRIBE_TICKET.getCommandName(), new SubscribeTicketCommand(sendBotMessageService,flightsClient,flightService))
                .put(SHARE_LOCATION.getCommandName(), new ShareLocationCommand(sendBotMessageService, telegramUserService, localisationService))
                .put(UPDATE_LOCALISATIONS.getCommandName(),new UpdateLocalisationsCommand(localisationClient,localisationService))
                .put(FIND_FLIGHT.getCommandName(),new FindFlightCommand(sendBotMessageService))
                .put(UPDATE_AIRPORTS.getCommandName(),new UpdateAirportsCommand(airportsClient,airportService))
                .put(SEARCH_AIRPORT.getCommandName(), new ParameterHandleCommand(sendBotMessageService, airportService))
                .put(FROM.getCommandName(),new ParameterRequestCommand(sendBotMessageService))
                .put(TO.getCommandName(),new ParameterRequestCommand(sendBotMessageService))
                .put(DEPART.getCommandName(),new ParameterRequestCommand(sendBotMessageService))
                .put(RETURN.getCommandName(),new ParameterRequestCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }


}
