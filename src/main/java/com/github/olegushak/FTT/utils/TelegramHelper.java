package com.github.olegushak.FTT.utils;

import com.github.olegushak.FTT.command.CommandName;
import com.github.olegushak.FTT.dto.AgentDto;
import com.github.olegushak.FTT.dto.PricingOptionsDto;
import com.github.olegushak.FTT.repository.entity.Airport;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.github.olegushak.FTT.command.CommandName.FOLLOW_TICKET;
import static com.github.olegushak.FTT.command.CommandName.SELECT_TICKET;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.DEPART;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.FROM;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.RETURN;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.SEARCH;
import static com.github.olegushak.FTT.command.search_command.FlightFormCommandName.TO;

public class TelegramHelper {

    public static InlineKeyboardMarkup createInlineKeyboard(Set<CommandName> commands) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        for (CommandName command : commands) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(command.getTitle());
            inlineKeyboardButton.setCallbackData(command.getCommandName());
            buttonsRow.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup createOneButton(String command, Boolean requestLocation) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText(command);
        button.setRequestLocation(requestLocation);
        keyboardRow.add(button);
        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createSearchForm() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow3 = new ArrayList<>();

        InlineKeyboardButton fromButton = new InlineKeyboardButton();
        fromButton.setText(FROM.getTitle());
        fromButton.setCallbackData(FROM.getCommandName());

        InlineKeyboardButton toButton = new InlineKeyboardButton();
        toButton.setText(TO.getTitle());
        toButton.setCallbackData(TO.getCommandName());

        InlineKeyboardButton depButton = new InlineKeyboardButton();
        depButton.setText(DEPART.getTitle());
        depButton.setCallbackData(DEPART.getCommandName());

        InlineKeyboardButton retButton = new InlineKeyboardButton();
        retButton.setText(RETURN.getTitle());
        retButton.setCallbackData(RETURN.getCommandName());

        InlineKeyboardButton searchButton = new InlineKeyboardButton();
        searchButton.setText(SEARCH.getTitle());
        searchButton.setCallbackData(SEARCH.getCommandName());

        buttonsRow1.add(fromButton);
        buttonsRow1.add(toButton);

        buttonsRow2.add(depButton);
        buttonsRow2.add(retButton);

        buttonsRow3.add(searchButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonsRow1);
        rowList.add(buttonsRow2);
        rowList.add(buttonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup createReplyKeyboard(List<String> commands,String emoji) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String command : commands) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(emoji + command);
            row.add(button);
            keyboard.add(row);
        }
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createAirportsKeyboard(List<Airport> airports) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (Airport airport: airports){
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.format("%s, %s ",airport.getName(),airport.getLocation()));
            button.setCallbackData(String.format(airport.getIata()));
            buttonsRow.add(button);
            rowList.add(buttonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup ticketHandleButton(String price_id){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>();

        InlineKeyboardButton selectButton = new InlineKeyboardButton();
        selectButton.setText(SELECT_TICKET.getTitle());
        selectButton.setCallbackData(SELECT_TICKET.getCommandName() + " " + price_id);

        InlineKeyboardButton followButton = new InlineKeyboardButton();
        followButton.setText(FOLLOW_TICKET.getTitle());
        followButton.setCallbackData(FOLLOW_TICKET.getCommandName() + " " + price_id);


        buttonsRow1.add(selectButton);
        buttonsRow2.add(followButton);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonsRow1);
        rowList.add(buttonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup referencesHandleButton(List<PricingOptionsDto> pricingOptions){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (PricingOptionsDto option: pricingOptions) {
            AgentDto agent = option.getAgents().get(0);
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.format("Buy ticket on %s",agent.getName() ));
            button.setUrl(agent.getUrl());
            buttonsRow.add(button);
            rowList.add(buttonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}


