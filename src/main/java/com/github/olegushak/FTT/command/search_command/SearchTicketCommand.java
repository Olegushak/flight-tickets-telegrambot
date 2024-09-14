package com.github.olegushak.FTT.command.search_command;

import com.github.olegushak.FTT.command.CommandUtils;
import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.PriceDto;
import com.github.olegushak.FTT.dto.SegmentDto;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.utils.CacheStore;
import com.github.olegushak.FTT.utils.DateParser;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import com.github.olegushak.FTT.utils.TelegramHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.SEARCH;

@Service
public class SearchTicketCommand extends BaseProcessor {

    private final SendBotMessageService sendBotMessageService;

    private final CacheStore<FlightReviewEntity> flightReviewCache;

    private final FlightService flightService;

    @Autowired
    public SearchTicketCommand(SendBotMessageService sendBotMessageService, CacheStore<FlightReviewEntity> flightReviewCache, FlightService flightService) {
        this.sendBotMessageService = sendBotMessageService;
        this.flightReviewCache = flightReviewCache;
        this.flightService = flightService;
    }


    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery() && CommandUtils.getCallbackData(update).equals(SEARCH.getCommandName())) {
            String chat_id = CommandUtils.getCallbackDataChatId(update);
            FlightRequestArgs args = FlightRequestArgs.builder()
                    .fromEntityId("HAN")
                    .toEntityId("SGN")
                    .departDate("2024-10-30")
                    .returnDate("2024-11-01")
                    .market("VN")
                    .locale("vi-VN")
                    .currency("VND")
                    .build();

//            FlightRequestArgs args = flightRequestCacheStore.get(chat_id);
            Map<String, FlightReviewEntity> results = flightService.roundTripSearch(args);
            flightReviewCache.putAll(results);
            int size = results.size();


            sendBotMessageService.sendPhoto(sendSearchResults(chat_id, size, args));
            results.values().forEach(s -> sendBotMessageService.sendMessage(foundTickets(chat_id, s)));
        } else {
            getDownstreamProcessor().execute(update);
        }
    }

    private SendPhoto sendSearchResults(String chatId, int resultsSize, FlightRequestArgs args) {
        String fromId = args.getFromEntityId();
        String toId = args.getToEntityId();
        String depDate = args.getDepartDate();
        String redDate = args.getReturnDate();
        File photo = new File("src/main/resources/photo/search_result.png");

        String message = String.format("FLIGHT\nFROM: %s \nTO: %s \nDEPARTURE DATE: %s RETURN DATE: %s \n\n%s results for your search"
                , fromId, toId, depDate, redDate, resultsSize);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setCaption(String.format(message));
        return sendPhoto;
    }

    private SendMessage foundTickets(String chat_id, FlightReviewEntity flight) {
        String format = ("%s            %s            %s\n" +
                "           -------------------\n" +
                "%s             %s                %s\n\n" +
                "%s             %s            %s\n" +
                "           ------------------- \n" +
                "%s           %s             %s\n\n" +
                "-----------------------------------\n\n" +
                "Price: %s   ");

        List<LegDto> legs = flight.getLegs();

        LegDto legForward = legs.get(0);
        LegDto legBackTrack = legs.get(1);
        String depTimeFrwd = DateParser.parseTime(legForward.getDeparture());
        String arrTimeFrwd = DateParser.parseTime(legForward.getArrival());
        String depTimeBack = DateParser.parseTime(legBackTrack.getDeparture());
        String arrTimeBack = DateParser.parseTime(legBackTrack.getArrival());
        String stopsFrwd = checkStops(legForward);
        String durationFrwd = DateParser.parseDuration(legForward.getDurationInMinutes());
        String depArptFrwd = legForward.getOrigin().getId();
        String arrArptFrwrd = legForward.getDestination().getId();
        String durationBack = DateParser.parseDuration(legBackTrack.getDurationInMinutes());
        String depArptBack = legBackTrack.getOrigin().getId();
        String arrArptBack = legBackTrack.getDestination().getId();
        String stopsBack = checkStops(legBackTrack);
        PriceDto price = flight.getPrice();
        String formattedPrice = price.getFormatted();

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(String.format(
                format,
                depTimeFrwd,
                durationFrwd,
                arrTimeFrwd,
                depArptFrwd,
                stopsFrwd,
                arrArptFrwrd,
                depTimeBack,
                durationBack,
                arrTimeBack,
                depArptBack,
                stopsBack,
                arrArptBack,
                formattedPrice));
        message.setReplyMarkup(TelegramHelper.ticketHandleButton(price.getPricingOptionId()));

        return message;
    }

    public String checkStops(LegDto legDto) {
        int stops = legDto.getStops();
        switch (stops) {
            case 0 -> {
                return "Direct";
            }

            case 1 -> {
                return "1 stop " + legDto.getSegments().get(0).getDestination().getParent().getDisplayCode();
            }

            default -> {
                StringBuilder result = new StringBuilder(stops + "  stops ");
                List<SegmentDto> segments = legDto.getSegments();
                segments.stream()
                        .limit(segments.size() - 1)
                        .map(s -> result.append(s.getDestination().getParent().getDisplayCode()).append(", "));
                result.deleteCharAt(result.lastIndexOf(","));
                return result.toString();
            }

        }
    }


}
