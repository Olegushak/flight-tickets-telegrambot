package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.CommandName.STAT;
import static com.github.olegushak.FTT.command.CommandUtils.getText;

@Service
public class StatCommand extends BaseProcessor {

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private SendBotMessageService sendBotMessageService;
    public final static String STAT_MESSAGE = "Find flight tickets Telegram Bot использует %s человек.";


    @Override
    public void execute(Update update) {
        if ((update.hasMessage() && getText(update).equals(STAT.getCommandName()))) {
            int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE, activeUserCount));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }
}
