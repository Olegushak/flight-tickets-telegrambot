package com.github.olegushak.FTT.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.DEPART;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.FROM;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.RETURN;
import static com.github.olegushak.FTT.command.searchCommand.FlightFormCommandName.TO;

public class TelegramHelper {

    public static InlineKeyboardMarkup createInlineKeyboard(Set<String> commands) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        for (String command : commands) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(command);
            inlineKeyboardButton.setCallbackData(command);
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
        String fromCommand = FROM.getCommandName();
        String toCommand = TO.getCommandName();
        String depCommand = DEPART.getCommandName();
        String retCommand = RETURN.getCommandName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow2 = new ArrayList<>();

        InlineKeyboardButton fromButton = new InlineKeyboardButton();
        fromButton.setText(fromCommand);
        fromButton.setCallbackData(fromCommand);

        InlineKeyboardButton toButton = new InlineKeyboardButton();
        toButton.setText(toCommand);
        toButton.setCallbackData(toCommand);

        InlineKeyboardButton depButton = new InlineKeyboardButton();
        depButton.setText(depCommand);
        depButton.setCallbackData(depCommand);

        InlineKeyboardButton retButton = new InlineKeyboardButton();
        retButton.setText(retCommand);
        retButton.setCallbackData(retCommand);

        buttonsRow1.add(fromButton);
        buttonsRow1.add(toButton);

        buttonsRow2.add(depButton);
        buttonsRow2.add(retButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonsRow1);
        rowList.add(buttonsRow2);
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
}


