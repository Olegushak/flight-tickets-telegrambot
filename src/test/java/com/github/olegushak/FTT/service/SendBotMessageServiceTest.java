package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private FlightTicketsFinderBot flightTicketsFinderBot;

    @BeforeEach
    public void init() {
        flightTicketsFinderBot = Mockito.mock(FlightTicketsFinderBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(flightTicketsFinderBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(flightTicketsFinderBot).execute(sendMessage);
    }


}
