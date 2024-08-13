package com.github.olegushak.FTT.command.searchCommand;

import com.github.olegushak.FTT.command.Command;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot.cacheStore;

public class FindFlightCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public FindFlightCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;

    }

    @Override
    public void execute(Update update) {
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        FlightRequestArgs args = new FlightRequestArgs();
        cacheStore.add(chat_id,args);
        sendBotMessageService.sendMessageWithKeyboard(flightForm(chat_id));
    }

    public SendMessage flightForm(String chatId){
        SendMessage message = new SendMessage();
        message.setText("Easily find and track your flight.");
        message.setChatId(chatId);
        message.setReplyMarkup(TelegramHelper.createSearchForm());
        return message;
    }
}
