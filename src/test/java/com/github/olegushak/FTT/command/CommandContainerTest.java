package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.AirportsClient;
import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.client.LocalisationClient;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.LocalisationService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        FlightsClient flightsClient = Mockito.mock(FlightsClient.class);
        FlightService flightService = Mockito.mock(FlightService.class);
        LocalisationService localisationService = Mockito.mock(LocalisationService.class);
        LocalisationClient localisationClient = Mockito.mock(LocalisationClient.class);
        AirportService airportService = Mockito.mock(AirportService.class);
        AirportsClient airportsClient = Mockito.mock(AirportsClient.class);
        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService,
                flightsClient,
                flightService,
                localisationService,
                localisationClient,
                airportService,
                airportsClient
                );
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/fgjhdfgdfg";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
