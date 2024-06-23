package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.olegushak.FTT.command.CommandName.HELP;
import static com.github.olegushak.FTT.command.CommandName.START;
import static com.github.olegushak.FTT.command.CommandName.STAT;
import static com.github.olegushak.FTT.command.CommandName.STOP;

public class HelpCommand implements Command{

    private final SendBotMessageService sendBotMessageService;


    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"

            + "<b>Начать\\закончить работу с ботом</b>\n"
            + "%s - начать работу со мной\n"
            + "%s - приостановить работу со мной\n"
            + "%s - получить помощь в работе со мной\n"
            + "%s - получить статистику",
            START.getCommandName(),STOP.getCommandName(),HELP.getCommandName(),STAT.getCommandName());

    public  HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),HELP_MESSAGE);
    }
}
