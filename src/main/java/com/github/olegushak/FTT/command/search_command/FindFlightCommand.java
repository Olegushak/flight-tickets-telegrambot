package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import static com.github.olegushak.FTT.command.CommandName.FIND_FLIGHT;
import static com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot.flightRequestCacheStore;

@Service
public class FindFlightCommand extends BaseProcessor {

    private final SendBotMessageService sendBotMessageService;

    private final StateMachine<ParameterState, ParameterEvent> stateMachine;

    private final TelegramUserService userService;

    @Autowired
    public FindFlightCommand(SendBotMessageService sendBotMessageService, StateMachine<ParameterState, ParameterEvent> stateMachine, TelegramUserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.stateMachine = stateMachine;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(FIND_FLIGHT.getCommandName())) {
            String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
            TelegramUser user = userService.findByChatId(chat_id).orElseThrow(NotFoundException::new);
            stateMachine.start();
            FlightRequestArgs args = FlightRequestArgs.builder()
                    .market(user.getMarket())
                    .locale(user.getLocale())
                    .currency(user.getCurrency())
                    .build();
            flightRequestCacheStore.add(chat_id, args);
            sendBotMessageService.sendMessageWithKeyboard(flightForm(chat_id));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }

    public SendMessage flightForm(String chatId){
        SendMessage message = new SendMessage();
        message.setText("Easily find and track your flight.");
        message.setChatId(chatId);
        message.setReplyMarkup(TelegramHelper.createSearchForm());
        return message;
    }
}
