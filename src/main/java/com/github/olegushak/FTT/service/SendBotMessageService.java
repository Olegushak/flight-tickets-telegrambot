package com.github.olegushak.FTT.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message);

    void sendMessageWithKeyboard(SendMessage sendMessage);

    void sendMessage(SendMessage sendMessage);

    void sendPhoto(SendPhoto sendPhoto);
}

