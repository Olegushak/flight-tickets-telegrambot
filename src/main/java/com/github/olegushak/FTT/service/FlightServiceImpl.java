package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.DestinationDto;
import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.OriginDto;
import com.github.olegushak.FTT.repository.FlightRepository;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final TelegramUserService telegramUserService;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, TelegramUserService telegramUserService) {
        this.flightRepository = flightRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void save(String chatId, ItineraryDto itinerary,String token) {
        TelegramUser user = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);

        Flight flightToSave;
        Optional<Flight> flightFromDB = flightRepository.findById(itinerary.getId());
        if(flightFromDB.isPresent()) {
            flightToSave = flightFromDB.get();
            Optional<TelegramUser> first = flightToSave.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                flightToSave.addUser(user);
            }
        } else {
            flightToSave = toEntity(itinerary,token);
            flightToSave.addUser(user);
        }
        flightRepository.save(flightToSave);
    }

    @Override
    public Flight toEntity(ItineraryDto itinerary,String token){
        LegDto leg = itinerary.getLegs().get(0);
        OriginDto origin = leg.getOrigin();
        DestinationDto destination = leg.getDestination();
        return Flight.builder()
                .id(itinerary.getId())
                .departure(origin.getId())
                .fromCountry(origin.getCountry())
                .fromCity(origin.getCity())
                .destination(destination.getId())
                .toCountry(destination.getCountry())
                .toCity(destination.getCity())
                .depTime(leg.getDeparture())
                .arrTime(leg.getArrival())
                .duration(leg.getDurationInMinutes())
                .price(itinerary.getPrice().getFormatted())
                .token(token)
                .build();
    }
}
