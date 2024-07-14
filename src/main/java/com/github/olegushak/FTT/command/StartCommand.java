package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import static com.github.olegushak.FTT.command.CommandName.SHARE_LOCATION;
import static com.github.olegushak.FTT.command.CommandUtils.getChatId;
import static com.github.olegushak.FTT.command.CommandUtils.getUsername;

public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public StartCommand(SendBotMessageService sendBotMessageService,TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    public final static String START_MESSAGE = "Hi %s!. I am \"Find flight tickets\" Telegram Bot. " +
            "I help you to find and follow flight tickets for your trip! First!" +
            " I need to get your location to determine the country.";
    @Override
    public void execute(Update update) {
        String chat_id = getChatId(update);
        String username = getUsername(update);

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
        sendBotMessageService.sendMessageWithKeyboard(startMessage(chat_id,username));
    }

    public SendMessage startMessage(String chatId,String username){
       SendMessage message = new SendMessage();
       message.setText(String.format(START_MESSAGE,username));
       message.setChatId(chatId);
       message.setReplyMarkup(TelegramHelper.createOneButton(SHARE_LOCATION.getCommandName(),true));
       return message;
    }
}
