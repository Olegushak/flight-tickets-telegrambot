package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.service.FlightService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SubscribeTicketCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    private final FlightsClient flightsClient;

    private final FlightService flightService;

    public SubscribeTicketCommand(SendBotMessageService sendBotMessageService, FlightsClient flightsClient, FlightService flightService) {
        this.sendBotMessageService = sendBotMessageService;
        this.flightsClient = flightsClient;
        this.flightService = flightService;
    }

    @Override
    public void execute(Update update) {
//        if (getMessage(update).equalsIgnoreCase(SUBSCRIBE_TICKET.getCommandName())) {
//            sendFlightSubscribed(getChatId(update));
//            return;
//        }
//        String flightId = getMessage(update).split(" ")[1];
//        String chatId = getChatId(update);
//        ItineraryDto flightById = flightsClient.;
//            if (isNull(groupById.getId())) {
//                sendGroupNotFound(chatId, groupId);
//            }
//            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
//            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
//        } else {
//            sendGroupNotFound(chatId, groupId);
//        }
    }

    private void sendFlightSubscribed(String chatId) {
        String message = "Вы успешно подписаны! Это позволит вам получать уведомления об изменении цены на выбранный вами билет. " +
                "Учитывайте, что цена может измениться как в меньшую так и в большую сторону! Удачи!";

        sendBotMessageService.sendMessage(chatId,message);
    }
}
