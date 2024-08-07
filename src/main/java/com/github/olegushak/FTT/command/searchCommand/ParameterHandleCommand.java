package com.github.olegushak.FTT.command.searchCommand;

import com.github.olegushak.FTT.command.Command;
import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.olegushak.FTT.utils.EmojiConst.ARR_AIRPLANE;
import static com.github.olegushak.FTT.utils.EmojiConst.DEP_AIRPLANE;

public class ParameterHandleCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public  final AirportService airportService;

    public ParameterHandleCommand(SendBotMessageService sendBotMessageService, AirportService airportService) {
        this.sendBotMessageService = sendBotMessageService;
        this.airportService = airportService;
    }

    @Override
    public void execute(Update update) {
        String repliedMessage = update.getMessage().getReplyToMessage().getText();
        String airportName = CommandUtils.getText(update);
        String chat_id = CommandUtils.getChatId(update);
        List<String> airports = airportService.search(airportName);
        if(repliedMessage.contains(DEP_AIRPLANE)){
            sendBotMessageService.sendMessageWithKeyboard(airportsList(chat_id,airports,DEP_AIRPLANE));
        } else if(repliedMessage.contains(ARR_AIRPLANE)) {
            sendBotMessageService.sendMessageWithKeyboard(airportsList(chat_id,airports,ARR_AIRPLANE));
        }
    }

    public SendMessage airportsList(String chat_id,List<String> airports,String emoji){
        SendMessage message = new SendMessage();
        message.setText("possible Airports");
        message.setChatId(chat_id);
        message.setReplyMarkup(TelegramHelper.createReplyKeyboard(airports,emoji));
        return message;
    }
}
