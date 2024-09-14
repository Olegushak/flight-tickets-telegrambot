package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.client.FlightsClient;
import com.github.olegushak.FTT.dto.DestinationDto;
import com.github.olegushak.FTT.dto.DetailedItineraryDto;
import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.OriginDto;
import com.github.olegushak.FTT.repository.FlightRepository;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.utils.DtoMapper;
import com.github.olegushak.FTT.utils.FlightRequestArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final TelegramUserService telegramUserService;

    private final FlightsClient flightsClient;

    private final DtoMapper dtoMapper;



    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, TelegramUserService telegramUserService, FlightsClient flightsClient, DtoMapper dtoMapper) {
        this.flightRepository = flightRepository;
        this.telegramUserService = telegramUserService;
        this.flightsClient = flightsClient;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void saveTicket(String chatId, FlightReviewEntity flightReviewEntity) {
        TelegramUser user = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);

        Flight flightToSave;
        Optional<Flight> flightFromDB = flightRepository.findByItineraryId(flightReviewEntity.getId());
        if(flightFromDB.isPresent()) {
            flightToSave = flightFromDB.get();
            Optional<TelegramUser> first = flightToSave.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                flightToSave.addUser(user);
            }
        } else {
            LegDto leg = flightReviewEntity.getLegs().get(0);
            OriginDto origin = leg.getOrigin();
            DestinationDto destination = leg.getDestination();
            flightToSave = Flight.builder()
                    .itineraryId(flightReviewEntity.getId())
                    .departure(origin.getId())
                    .fromCity(origin.getCity())
                    .fromCountry(origin.getCountry())
                    .destination(destination.getId())
                    .toCity(destination.getCity())
                    .toCountry(destination.getCountry())
                    .depTime(leg.getDeparture())
                    .arrTime(leg.getArrival())
                    .price(flightReviewEntity.getPrice().getFormatted())
                    .token(flightReviewEntity.getToken())
                    .build();
            flightToSave.addUser(user);
        }
       flightRepository.save(flightToSave);
    }

    @Override
    public Map<String, FlightReviewEntity> roundTripSearch(FlightRequestArgs flightRequestArgs) {
        FlightDto flightDto;
        try {
            flightDto = flightsClient.retrieveRoundTripFlights(flightRequestArgs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String token = flightDto.getToken();
        return flightDto.getItineraries().stream()
                .map(s -> dtoMapper.flightDtoToEntity(s,token))
                .collect(Collectors.toMap(s->s.getPrice().getPricingOptionId(), s-> s));

    }

    @Override
    public DetailedItineraryDto getFlightDetails(FlightRequestArgs flightRequestArgs) {
        FlightDetailsDto details;
        try {
            details = flightsClient.retrieveFlightDetails(flightRequestArgs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return details.getItinerary();
    }


}
