package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.CommandUtils.getCallbackData;
import static com.github.olegushak.FTT.command.CommandUtils.getCallbackDataChatId;
import static com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot.flightRequestCacheStore;

@Service
public class ParameterHandleCommand extends BaseProcessor {
    private final StateMachine<ParameterState, ParameterEvent>  stateMachine;
    private final String datePattern = "((18|19|20)[0-9]{2}[\\-.](0[13578]|1[02])[\\-.](0[1-9]|[12][0-9]|3[01]))|(18|19|20)[0-9]{2}[\\-.](0[469]|11)[\\-.](0[1-9]|[12][0-9]|30)|(18|19|20)[0-9]{2}[\\-.](02)[\\-.](0[1-9]|1[0-9]|2[0-8])|(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)[\\-.](02)[\\-.]29";
    private final String airportPattern = "[A-Z]{3}";

    @Autowired
    public ParameterHandleCommand(StateMachine<ParameterState, ParameterEvent> stateMachine, SendBotMessageService sendBotMessageService, AirportService airportService) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void execute(Update update) {
        String chat_id;
        FlightRequestArgs args;
        String state;
        if (update.hasCallbackQuery() && getCallbackData(update).matches(airportPattern)) {
         chat_id = getCallbackDataChatId(update);
         String data = getCallbackData(update);
         args = flightRequestCacheStore.get(chat_id);
         state = stateMachine.getState().getId().name();
         if (state.equals(ParameterState.UPDATE_FROM.toString())) {
             args.setFromEntityId(data);
             stateMachine.sendEvent(ParameterEvent.FROM_SETTED);
         } else if (state.equals(ParameterState.UPDATE_TO.toString())) {
             args.setToEntityId(data);
             stateMachine.sendEvent(ParameterEvent.TO_SETTED);
         }
            flightRequestCacheStore.add(chat_id, args);
       } else if (update.hasMessage() && update.getMessage().hasText()) {
            String text = CommandUtils.getText(update);
            chat_id = update.getMessage().getChatId().toString();
            args = flightRequestCacheStore.get(chat_id);
            state = stateMachine.getState().getId().name();
            if(text.matches(datePattern)) {
                if (state.equals(ParameterState.UPDATE_DEP_DATE.toString())) {
                    args.setDepartDate(text);
                    stateMachine.sendEvent(ParameterEvent.DEP_DATE_SETTED);
                } else if (state.equals(ParameterState.UPDATE_RET_DATE.toString())) {
                    args.setReturnDate(text);
                    stateMachine.sendEvent(ParameterEvent.RET_DATE_SETTED);
                }
            }
            flightRequestCacheStore.add(chat_id, args);
        } else {
            getDownstreamProcessor().execute(update);
        }
    }
}
