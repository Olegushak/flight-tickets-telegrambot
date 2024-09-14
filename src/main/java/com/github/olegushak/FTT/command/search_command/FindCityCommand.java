package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.olegushak.FTT.command.CommandUtils.getState;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_FROM;
import static com.github.olegushak.FTT.statemachine.state.ParameterState.UPDATE_TO;

@Service
public class FindCityCommand extends BaseProcessor {

    private final AirportService airportService;

    private final SendBotMessageService sendBotMessageService;

    private final StateMachine<ParameterState, ParameterEvent>  stateMachine;

    @Autowired
    public FindCityCommand(AirportService airportService, SendBotMessageService sendBotMessageService, StateMachine<ParameterState, ParameterEvent> stateMachine) {
        this.airportService = airportService;
        this.sendBotMessageService = sendBotMessageService;
        this.stateMachine = stateMachine;
    }


    @Override
    public void execute(Update update) {
        if(update.hasMessage() && update.getMessage().hasText() &&
                (getState(stateMachine).equals(UPDATE_FROM.toString()) || getState(stateMachine).equals(UPDATE_TO.toString()))){
            String text = CommandUtils.getText(update);
            String chat_id = CommandUtils.getChatId(update);
            List<Airport> airports = airportService.search(text);
            sendBotMessageService.sendMessageWithKeyboard(airportsList(chat_id, airports));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }

    public SendMessage airportsList(String chat_id, List<Airport> airports) {
        SendMessage message = new SendMessage();
        message.setText("possible Airports");
        message.setChatId(chat_id);
        message.setReplyMarkup(TelegramHelper.createAirportsKeyboard(airports));
        return message;
    }
}
