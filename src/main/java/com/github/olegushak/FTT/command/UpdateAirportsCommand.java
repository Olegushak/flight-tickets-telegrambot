package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.AirportsClient;
import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.processor.BaseProcessor;
import com.github.olegushak.FTT.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

import static com.github.olegushak.FTT.command.CommandName.UPDATE_AIRPORTS;
import static com.github.olegushak.FTT.command.CommandUtils.getText;

@Service
public class UpdateAirportsCommand extends BaseProcessor {

    private final AirportsClient airportsClient;

    private final AirportService airportService;

    @Autowired
    public UpdateAirportsCommand(AirportsClient airportsClient, AirportService airportService) {
        this.airportsClient = airportsClient;
        this.airportService = airportService;
    }

    @Override
    public void execute(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() && getText(update).equals(UPDATE_AIRPORTS.getCommandName())) {
            try {
                System.out.println("start deleting");
                airportService.deleteAll();
                System.out.println("deleting finished");
                System.out.println("start updating");
                List<AirportDto> airports = airportsClient.retrieveAirports();
                airportService.saveAll(airports);
                System.out.println("updating finished");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            getDownstreamProcessor().execute(update);
        }
    }
}
