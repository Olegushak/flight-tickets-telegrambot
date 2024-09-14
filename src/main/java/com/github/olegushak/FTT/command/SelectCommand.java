package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.dto.DetailedItineraryDto;
import com.github.olegushak.FTT.dto.PricingOptionsDto;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.List;

import static com.github.olegushak.FTT.command.CommandName.SELECT_TICKET;
import static com.github.olegushak.FTT.command.CommandUtils.getCallbackData;


@Service
public class SelectCommand extends BaseProcessor {

    private final FlightService flightService;

    private final CacheStore<FlightReviewEntity> flightReviewCache;

    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public SelectCommand(FlightService flightService, CacheStore<FlightReviewEntity> flightReviewCache, SendBotMessageService sendBotMessageService) {
        this.flightService = flightService;
        this.flightReviewCache = flightReviewCache;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery() && CommandUtils.getCallbackData(update).startsWith(SELECT_TICKET.getCommandName())){
            String itinerary_id = getCallbackData(update).split(" ")[1];
            String chat_id = CommandUtils.getCallbackDataChatId(update);
            FlightReviewEntity reviewEntity = flightReviewCache.get(itinerary_id);
            FlightRequestArgs args = FlightRequestArgs.builder()
                    .itineraryId(reviewEntity.getId())
                    .token(reviewEntity.getToken())
                    .build();
            DetailedItineraryDto detailedItineraryDto = flightService.getFlightDetails(args);
            sendBotMessageService.sendPhoto(sendReferencesForBuy(chat_id,detailedItineraryDto));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }

    private SendPhoto sendReferencesForBuy(String chatId, DetailedItineraryDto detailedItineraryDto) {

        List<PricingOptionsDto> pricingOptions = detailedItineraryDto.getPricingOptions();

//        String fromId = detailedItineraryDto;
//        String toId = args.getToEntityId();
//        String depDate = args.getDepartDate();
//        String redDate = args.getReturnDate();
        File photo = new File("src/main/resources/photo/buy_from_me.png");
//
//        String message = String.format("FLIGHT\nFROM: %s \nTO: %s \nDEPARTURE DATE: %s RETURN DATE: %s"
//                , fromId, toId, depDate, redDate);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setCaption("references");
        sendPhoto.setReplyMarkup(TelegramHelper.referencesHandleButton(pricingOptions));
        return sendPhoto;
    }
}
