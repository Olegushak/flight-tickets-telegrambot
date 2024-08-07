package com.github.olegushak.FTT.command;

import com.github.olegushak.FTT.client.AirportsClient;
import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.service.AirportService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public class UpdateAirportsCommand implements Command{

    private final AirportsClient airportsClient;

    private final AirportService airportService;

    public UpdateAirportsCommand(AirportsClient airportsClient, AirportService airportService) {
        this.airportsClient = airportsClient;
        this.airportService = airportService;
    }

    @Override
    public void execute(Update update) {
        try {
            System.out.println("start deleting");
            airportService.deleteAll();
            System.out.println("deleting finished");
            System.out.println("start updating");
            List<AirportDto> airports =  airportsClient.retrieveAirports();
            airportService.saveAll(airports);
            System.out.println("updating finished");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
