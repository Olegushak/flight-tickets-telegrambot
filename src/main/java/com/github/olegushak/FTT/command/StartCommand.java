package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public final static String START_MESSAGE = "Привет. Я \"Find flight tickets\" Telegram Bot. Я помогу тебе найти и отследить билеты для твоего путешествия ";
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
