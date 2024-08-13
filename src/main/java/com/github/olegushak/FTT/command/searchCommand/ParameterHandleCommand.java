package com.github.olegushak.FTT.command.searchCommand;

import com.github.olegushak.FTT.command.Command;
import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.service.AirportService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.Emoji;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot.cacheStore;
import static com.github.olegushak.FTT.utils.Emoji.ARR_AIRPLANE;
import static com.github.olegushak.FTT.utils.Emoji.DEP_AIRPLANE;
import static com.github.olegushak.FTT.utils.Emoji.RETURN_DATE;


public class ParameterHandleCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private final AirportService airportService;

    public ParameterHandleCommand(SendBotMessageService sendBotMessageService, AirportService airportService) {
        this.sendBotMessageService = sendBotMessageService;
        this.airportService = airportService;

    }

    @Override
    public void execute(Update update) {
        FlightRequestArgs args = null;
        String chat_id = "";
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            chat_id = query.getMessage().getChatId().toString();
            String data = query.getData();
            args = cacheStore.get(chat_id);
            if(data.contains(DEP_AIRPLANE.getPicture())){
                args.setFromEntityId(data);
            } else if(data.contains(ARR_AIRPLANE.getPicture())){
                args.setToEntityId(data);
            }
        } else {
            String repliedMessage = update.getMessage().getReplyToMessage().getText();
            String text = CommandUtils.getText(update);
            chat_id = CommandUtils.getChatId(update);
            List<Airport> airports;
            if (repliedMessage.contains(DEP_AIRPLANE.getPicture())) {
                airports = airportService.search(text);
                sendBotMessageService.sendMessageWithKeyboard(airportsList(chat_id, airports, DEP_AIRPLANE));
            } else if (repliedMessage.contains(ARR_AIRPLANE.getPicture())) {
                airports = airportService.search(text);
                sendBotMessageService.sendMessageWithKeyboard(airportsList(chat_id, airports, ARR_AIRPLANE));
            } else if (repliedMessage.contains(Emoji.DEP_DATE.getPicture())) {
                args = cacheStore.get(chat_id);
                args.setDepartDate(text);
            } else if (repliedMessage.contains(RETURN_DATE.getPicture())) {
                args = cacheStore.get(chat_id);
                args.setReturnDate(text);
            }
        }
        cacheStore.add(chat_id, args);
    }

    public SendMessage airportsList(String chat_id, List<Airport> airports, Emoji emoji) {
        SendMessage message = new SendMessage();
        message.setText("possible Airports");
        message.setChatId(chat_id);
        message.setReplyMarkup(TelegramHelper.createAirportsKeyboard(airports, emoji));
        return message;
    }
}
