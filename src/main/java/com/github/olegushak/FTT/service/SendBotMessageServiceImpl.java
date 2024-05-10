package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final FlightTicketsFinderBot flightTicketsFinderBot;

    @Autowired
    public SendBotMessageServiceImpl(FlightTicketsFinderBot flightTicketsFinderBot){
        this.flightTicketsFinderBot = flightTicketsFinderBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            flightTicketsFinderBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }

    }
}
