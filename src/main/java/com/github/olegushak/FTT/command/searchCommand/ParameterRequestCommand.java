package com.github.olegushak.FTT.command.searchCommand;

import com.github.olegushak.FTT.command.Command;
import com.github.olegushak.FTT.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.DEPART;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.FROM;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.RETURN;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.TO;
import static com.github.olegushak.FTT.utils.EmojiConst.ARR_AIRPLANE;
import static com.github.olegushak.FTT.utils.EmojiConst.DEP_AIRPLANE;
import static com.github.olegushak.FTT.utils.EmojiConst.DEP_DATE;
import static com.github.olegushak.FTT.utils.EmojiConst.RETURN_DATE;

public class ParameterRequestCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String SEARCH_MESSAGE = "Enter the city name:";

    public final static String DATE_MESSAGE = "Enter date (format: \"YYYY-MM-DD\")";

    public ParameterRequestCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        String chat_id = query.getMessage().getChatId().toString();
        String data = query.getData();
        String message = "";
        String emoji = "";
        if(data.equals(FROM.getCommandName())){
            message = SEARCH_MESSAGE;
            emoji = DEP_AIRPLANE;
        } else if(data.equals(TO.getCommandName())){
            message = SEARCH_MESSAGE;
            emoji = ARR_AIRPLANE;
        } else if (data.equals(DEPART.getCommandName())) {
            message = DATE_MESSAGE;
            emoji = DEP_DATE;
        } else if (data.equals(RETURN.getCommandName())) {
            message = DATE_MESSAGE;
            emoji = RETURN_DATE;
        }
        sendBotMessageService.sendMessage(requestSearchParameter(chat_id,emoji + message));


    }


    public SendMessage requestSearchParameter(String chat_id,String message){
        SendMessage requestCityMessage = new SendMessage();
        requestCityMessage.setChatId(chat_id);
        requestCityMessage.setText(message);
        return requestCityMessage;
    }
}
