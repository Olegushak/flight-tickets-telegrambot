package com.github.olegushak.FTT.telegrambot;

import com.github.olegushak.FTT.command.MenuCommand;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class FlightTicketsFinderBot extends TelegramLongPollingBot {

    @Value("${telegram.name}")
    private String botUsername;
    @Value("${telegram.token}")
    private String botToken;

    private final BaseProcessor telegramBotProcessor;

    public static CacheStore<FlightRequestArgs> flightRequestCacheStore;

    @Autowired
    public FlightTicketsFinderBot(BaseProcessor telegramBotProcessor, CacheStore<FlightRequestArgs> cacheStore) {
        this.telegramBotProcessor = telegramBotProcessor;
        FlightTicketsFinderBot.flightRequestCacheStore = cacheStore;
    }

    @PostConstruct
    public void menuButtons()throws TelegramApiException  {
      this.execute(new SetMyCommands(MenuCommand.getMenuCommands(),new BotCommandScopeDefault(),null));
    }

    @Override
    public void onUpdateReceived(Update update) {
        telegramBotProcessor.execute(update);
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
