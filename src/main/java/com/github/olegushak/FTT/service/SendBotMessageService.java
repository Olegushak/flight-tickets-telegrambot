package com.github.olegushak.FTT.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message);

    void sendMessageWithKeyboard(SendMessage sendMessage);
}
