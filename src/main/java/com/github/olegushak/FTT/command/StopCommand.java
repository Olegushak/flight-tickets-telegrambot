package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService){
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chat_id = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chat_id, STOP_MESSAGE);
        telegramUserService.findByChatId(chat_id)
                .ifPresent(it -> {
                    it.setActive(false);
                    telegramUserService.save(it);
                });
    }
}
