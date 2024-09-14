package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.statemachine.event.ParameterEvent;
import com.github.olegushak.FTT.statemachine.state.ParameterState;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {

    public static String getChatId(Update update){
        return update.getMessage().getChatId().toString();
    }

    public static String getText(Update update){
        return update.getMessage().getText();
    }

    public static String getUsername(Update update){return update.getMessage().getFrom().getUserName();}

    public static String getCallbackData(Update update){
        return update.getCallbackQuery().getData();
    }

    public static String getState(StateMachine<ParameterState, ParameterEvent>  stateMachine){
        return stateMachine.getState().getId().name();
    }

    public static String getCallbackDataChatId(Update update){
        return update.getCallbackQuery().getMessage().getChatId().toString();
    }
}
