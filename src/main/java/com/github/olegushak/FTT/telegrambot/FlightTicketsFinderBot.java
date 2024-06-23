package com.github.olegushak.FTT.telegrambot;

import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.command.CommandContainer;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.SendBotMessageServiceImpl;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.CommandName.NO;

@Component
public class FlightTicketsFinderBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${telegram.name}")
    private String botUsername;

    @Value("${telegram.token}")
    private String botToken;

    private final CommandContainer commandContainer;

    @Autowired
    public FlightTicketsFinderBot(TelegramUserService telegramUserService, FlightService flightService, FlightsClient flightsClient){
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),telegramUserService,flightsClient,flightService);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
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
