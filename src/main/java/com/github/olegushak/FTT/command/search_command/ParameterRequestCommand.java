package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.DEPART;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.FROM;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.RETURN;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.TO;
import static com.github.olegushak.FTT.utils.Emoji.ARR_AIRPLANE;
import static com.github.olegushak.FTT.utils.Emoji.DEP_AIRPLANE;
import static com.github.olegushak.FTT.utils.Emoji.DEP_DATE;
import static com.github.olegushak.FTT.utils.Emoji.RETURN_DATE;

@Service
public class ParameterRequestCommand extends BaseProcessor {

    private final SendBotMessageService sendBotMessageService;

    private final StateMachine<ParameterState, ParameterEvent> stateMachine;

    public final static String SEARCH_MESSAGE = "Enter the city name:";

    public final List<String> commands = List.of(FROM.getCommandName(),TO.getCommandName(),DEPART.getCommandName(),RETURN.getCommandName());

    public final static String DATE_MESSAGE = "Enter date (format: \"YYYY-MM-DD\")";

    @Autowired
    public ParameterRequestCommand(SendBotMessageService sendBotMessageService, StateMachine<ParameterState, ParameterEvent> stateMachine) {
        this.sendBotMessageService = sendBotMessageService;
        this.stateMachine = stateMachine;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery() && commands.contains(CommandUtils.getCallbackData(update))){
            CallbackQuery query = update.getCallbackQuery();
            String chat_id = query.getMessage().getChatId().toString();
            String data = query.getData();
            String message = "";
            String emoji = "";
            if (data.equals(FROM.getCommandName())) {
                stateMachine.sendEvent(ParameterEvent.SET_FROM);
                message = SEARCH_MESSAGE;
                emoji = DEP_AIRPLANE.getPicture();
            } else if (data.equals(TO.getCommandName())) {
                stateMachine.sendEvent(ParameterEvent.SET_TO);
                message = SEARCH_MESSAGE;
                emoji = ARR_AIRPLANE.getPicture();
            } else if (data.equals(DEPART.getCommandName())) {
                stateMachine.sendEvent(ParameterEvent.SET_DEP_DATE);
                message = DATE_MESSAGE;
                emoji = DEP_DATE.getPicture();
            } else if (data.equals(RETURN.getCommandName())) {
                stateMachine.sendEvent(ParameterEvent.SET_RET_DATE);
                message = DATE_MESSAGE;
                emoji = RETURN_DATE.getPicture();
            }
            sendBotMessageService.sendMessage(requestSearchParameter(chat_id, emoji + message));
        } else {
            getDownstreamProcessor().execute(update);
        }

    }


    public SendMessage requestSearchParameter(String chat_id,String message){
        SendMessage requestCityMessage = new SendMessage();
        requestCityMessage.setChatId(chat_id);
        requestCityMessage.setText(message);
        return requestCityMessage;
    }
}
