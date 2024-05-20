package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public StartCommand(SendBotMessageService sendBotMessageService,TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    public final static String START_MESSAGE = "Привет. Я \"Find flight tickets\" Telegram Bot. Я помогу тебе найти и отследить билеты для твоего путешествия ";
    @Override
    public void execute(Update update) {
        String chat_id = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chat_id).ifPresentOrElse(
                user -> {
                   user.setActive(true);
                   telegramUserService.save(user);
                },
                ()-> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chat_id);
                    telegramUserService.save(telegramUser);
                }
        );

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
