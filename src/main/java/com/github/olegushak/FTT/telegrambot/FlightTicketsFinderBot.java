package com.github.olegushak.FTT.telegrambot;

import com.github.olegushak.FTT.client.AirportsClient;
import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.client.LocalisationClient;
import com.github.olegushak.FTT.command.MainCommandsContainer;
import com.github.olegushak.FTT.command.MenuCommand;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.LocalisationService;
import com.github.olegushak.FTT.service.SendBotMessageServiceImpl;
import com.github.olegushak.FTT.service.TelegramUserService;
import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;

import static com.github.olegushak.FTT.command.CommandName.NO_COMMAND;
import static com.github.olegushak.FTT.command.CommandName.SEARCH_AIRPORT;
import static com.github.olegushak.FTT.command.CommandName.SHARE_LOCATION;



@Component
public class FlightTicketsFinderBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";
    @Value("${telegram.name}")
    private String botUsername;
    @Value("${telegram.token}")
    private String botToken;
    private final MainCommandsContainer commandContainer;

    public static CacheStore<FlightRequestArgs> cacheStore = new CacheStore<>(2,TimeUnit.HOURS);

    @Autowired
    public FlightTicketsFinderBot(TelegramUserService telegramUserService, FlightService flightService, FlightsClient flightsClient, LocalisationService localisationService,
                                  LocalisationClient localisationClient, AirportsClient airportsClient, AirportService airportService){
        this.commandContainer = new MainCommandsContainer(new SendBotMessageServiceImpl(this),telegramUserService,flightsClient,flightService,localisationService,localisationClient,airportService,airportsClient);
    }

    @PostConstruct
    public void menuButtons()throws TelegramApiException  {
       this.execute(new SetMyCommands(MenuCommand.getMenuCommands(),new BotCommandScopeDefault(),null));

    }

    @Override
    public void onUpdateReceived(Update update) {
        if ((update.hasMessage())) {
            Message message = update.getMessage();
            if (message.hasLocation()) {
                commandContainer.retrieveCommand(SHARE_LOCATION.getCommandName()).execute(update);
            } else if (message.isReply()) {
                commandContainer.retrieveCommand(SEARCH_AIRPORT.getCommandName()).execute(update);
            } else if (message.hasText()) {
                String text = message.getText();
                if (text.startsWith(COMMAND_PREFIX)) {
                    String commandIdentifier = text.trim().split(" ")[0].toLowerCase();
                    commandContainer.retrieveCommand(commandIdentifier).execute(update);
                } else {
                    commandContainer.retrieveCommand(NO_COMMAND.getCommandName()).execute(update);
                }
            }

        } else if (update.hasCallbackQuery()) {
                CallbackQuery query = update.getCallbackQuery();
                String data = query.getData();
                if(data.startsWith(COMMAND_PREFIX)) {
                    commandContainer.retrieveCommand(data).execute(update);
                } else {
                    commandContainer.retrieveCommand(SEARCH_AIRPORT.getCommandName()).execute(update);
                }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
