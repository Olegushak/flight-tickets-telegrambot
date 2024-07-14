package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class FindFlightCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    private final TelegramUserService telegramUserService;

    private final FlightsClient flightsClient;


    public FindFlightCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, FlightsClient flightsClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.flightsClient = flightsClient;
    }

    @Override
    public void execute(Update update) {


    }
}
