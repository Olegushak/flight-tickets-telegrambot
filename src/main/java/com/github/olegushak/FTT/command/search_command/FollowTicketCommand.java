package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.CacheStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

import static com.github.olegushak.FTT.command.CommandName.FOLLOW_TICKET;
import static com.github.olegushak.FTT.command.CommandUtils.getCallbackData;

@Service
public class FollowTicketCommand extends BaseProcessor {

    private final FlightService flightService;

    private final CacheStore<FlightReviewEntity> flightReviewCache;

    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FollowTicketCommand(FlightService flightService, CacheStore<FlightReviewEntity> flightReviewCache, SendBotMessageService sendBotMessageService) {
        this.flightService = flightService;
        this.flightReviewCache = flightReviewCache;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery() && CommandUtils.getCallbackData(update).startsWith(FOLLOW_TICKET.getCommandName())) {
            String itinerary_id = getCallbackData(update).split(" ")[1];
            String chat_id = CommandUtils.getCallbackDataChatId(update);
            FlightReviewEntity reviewEntity = flightReviewCache.get(itinerary_id);
            flightService.saveTicket(chat_id,reviewEntity);
            sendBotMessageService.sendPhoto(followNotification(chat_id));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }

    private SendPhoto followNotification(String chatId) {

        String message = "Вы успешно подписаны! Это позволит вам получать уведомления об изменении цены на выбранный вами билет. " +
               "Учитывайте, что цена может измениться как в меньшую так и в большую сторону! Удачи!";

//        String fromId = detailedItineraryDto;
//        String toId = args.getToEntityId();
//        String depDate = args.getDepartDate();
//        String redDate = args.getReturnDate();
        File photo = new File("src/main/resources/photo/follow_ticket.png");
//
//        String message = String.format("FLIGHT\nFROM: %s \nTO: %s \nDEPARTURE DATE: %s RETURN DATE: %s"
//                , fromId, toId, depDate, redDate);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setCaption(message);
        return sendPhoto;
    }
}
