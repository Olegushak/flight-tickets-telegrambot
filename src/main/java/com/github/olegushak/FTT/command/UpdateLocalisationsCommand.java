package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.LocalisationClient;
import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.service.LocalisationService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public class UpdateLocalisationsCommand implements Command{

    private final LocalisationClient localisationClient;

    private final LocalisationService localisationService;

    public UpdateLocalisationsCommand(LocalisationClient localisationClient, LocalisationService localisationService) {
        this.localisationClient = localisationClient;
        this.localisationService = localisationService;
    }

    @Override
    public void execute(Update update) {
        try {
            System.out.println("start updating");
          List<LocalisationDto> localisations =  localisationClient.retrieveLocalisations();
            localisationService.saveAll(localisations);
            System.out.println("updating finished");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
