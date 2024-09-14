package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.CommandName.STOP;
import static com.github.olegushak.FTT.command.CommandUtils.getText;

@Service
public class StopCommand extends BaseProcessor {

    @Autowired
    private  SendBotMessageService sendBotMessageService;

    @Autowired
    private TelegramUserService telegramUserService;

    public static String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";


    @Override
    public void execute(Update update) {
        if ((update.hasMessage() && getText(update).equals(STOP.getCommandName()))) {
            String chat_id = update.getMessage().getChatId().toString();
            sendBotMessageService.sendMessage(chat_id, STOP_MESSAGE);
            telegramUserService.findByChatId(chat_id)
                    .ifPresent(it -> {
                        it.setActive(false);
                        telegramUserService.save(it);
                    });
        } else {
            getDownstreamProcessor().execute(update);
        }
    }
}
