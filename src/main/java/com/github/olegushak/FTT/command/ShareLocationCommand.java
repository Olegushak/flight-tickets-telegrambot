package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.repository.entity.Localisation;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.service.LocalisationService;
import com.github.olegushak.FTT.service.SendBotMessageService;
import com.github.olegushak.FTT.service.TelegramUserService;
import io.github.coordinates2country.Coordinates2Country;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

import static com.github.olegushak.FTT.utils.TelegramHelper.createInlineKeyboard;

public class ShareLocationCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private final TelegramUserService telegramUserService;

    private final LocalisationService localisationService;

    public ShareLocationCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, LocalisationService localisationService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.localisationService = localisationService;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        Location location = message.getLocation();
        String chat_id = message.getChatId().toString();
        String country = Coordinates2Country.country(location.getLatitude(), location.getLongitude());
        TelegramUser user = telegramUserService.findByChatId(chat_id).get();
        setLocation(user, country);
        sendBotMessageService.sendMessageWithKeyboard(locationConformation(chat_id));
    }

    public void setLocation(TelegramUser user, String country) {
        Localisation localisation = localisationService.findLocalisationByCountry(country).get();
        user.setMarket(localisation.getMarket());
        user.setLocale(localisation.getLocale());
        user.setCurrency(localisation.getCurrency());
        telegramUserService.save(user);
    }


    public SendMessage locationConformation(String chatId){
        SendMessage message = new SendMessage();
        message.setText("Your country: %s determine successfully");
        message.setChatId(chatId);
        message.setReplyMarkup(createInlineKeyboard(Set.of(CommandName.FIND_FLIGHT.getCommandName())));
        return message;
    }

}
