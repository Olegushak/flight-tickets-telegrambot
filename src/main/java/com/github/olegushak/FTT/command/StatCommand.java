package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command{

    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;
    public final static String STAT_MESSAGE = "Find flight tickets Telegram Bot использует %s человек.";

    public StatCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService){
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),String.format(STAT_MESSAGE,activeUserCount));

    }
}
