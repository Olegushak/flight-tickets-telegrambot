package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.SendBotMessageServiceImpl;
import com.github.olegushak.FTT.service.TelegramUserService;
import com.github.olegushak.FTT.telegrambot.FlightTicketsFinderBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract public class AbstractCommandTest {

    protected FlightTicketsFinderBot flightTicketsFinderBot = Mockito.mock(FlightTicketsFinderBot.class);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(flightTicketsFinderBot);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;
        String name = "Agent K";

        User user = new User();
        user.setUserName(name);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getFrom()).thenReturn(user);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(getCommandMessage(),name));
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(flightTicketsFinderBot).execute(sendMessage);
    }
}
