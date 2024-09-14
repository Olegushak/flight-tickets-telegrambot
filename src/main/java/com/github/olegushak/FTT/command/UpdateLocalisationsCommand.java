package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.LocalisationClient;
import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static com.github.olegushak.FTT.command.CommandName.UPDATE_LOCALISATIONS;
import static com.github.olegushak.FTT.command.CommandUtils.getText;

@Service
public class UpdateLocalisationsCommand extends BaseProcessor {

    private final LocalisationClient localisationClient;

    private final LocalisationService localisationService;

    @Autowired
    public UpdateLocalisationsCommand(LocalisationClient localisationClient, LocalisationService localisationService) {
        this.localisationClient = localisationClient;
        this.localisationService = localisationService;
    }

    @Override
    public void execute(Update update) {
        if ((update.hasMessage() && update.getMessage().hasText() && getText(update).equals(UPDATE_LOCALISATIONS.getCommandName()))) {
            try {
                System.out.println("start updating");
                List<LocalisationDto> localisations = localisationClient.retrieveLocalisations();
                localisationService.saveAll(localisations);
                System.out.println("updating finished");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            getDownstreamProcessor().execute(update);
        }

    }
}
