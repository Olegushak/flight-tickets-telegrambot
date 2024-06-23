package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.CarrierDto;
import com.github.olegushak.FTT.dto.DestinationDto;
import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.OriginDto;
import com.github.olegushak.FTT.dto.PriceDto;
import com.github.olegushak.FTT.repository.FlightRepository;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DisplayName("Unit-level testing for FlightService")
public class FlightServiceTest {
    private FlightService flightService;
    private FlightRepository flightRepository;
    private TelegramUser newUser;

    private final static String CHAT_ID = "1";

    @BeforeEach
    public void init(){
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        flightRepository = Mockito.mock(FlightRepository.class);
        flightService = new FlightServiceImpl(flightRepository,telegramUserService);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveFlight(){
        String token = "eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiUEFSSSIsImQiOiJNU1lBIiwiZDEiOiIyMDI0LTAyLTE1In0=";

        ItineraryDto itinerary = new ItineraryDto();
        itinerary.setId("15083-2402151840--30858,-31825-1-14355-2402160020");
        itinerary.setPrice(new PriceDto("$600"));
        List<LegDto> legs = new ArrayList<>();
        OriginDto origin = new OriginDto("HAN","Noi bai","Hanoi","Vietnam");
        DestinationDto destination = new DestinationDto("SGN","SaigonArpt","SGN City","Vietnam");
        LegDto leg = new LegDto(origin,destination,120,"2024-02-15T18:40:00","2024-02-15T20:40:00",new CarrierDto(null));
        legs.add(leg);
        itinerary.setLegs(legs);

        Flight expectedFlight = Flight.builder()
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
        expectedFlight.addUser(newUser);

        flightService.save(CHAT_ID,itinerary,token);

        Mockito.verify(flightRepository).save(expectedFlight);
    }

    @Test
    public void shouldProperlyAddUserForExistingFlight(){

        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId("2");
        oldTelegramUser.setActive(true);

        String token = "eyJhIjtyLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiUEFSSSIsImQiukJNU1lBIiwiZDEiOiIyMDI0LTAyLTE1In0=";

        ItineraryDto itinerary = new ItineraryDto();
        itinerary.setId("15045-4302151840--30858,-31825-1-14355-2402160020");
        itinerary.setPrice(new PriceDto("$600"));
        List<LegDto> legs = new ArrayList<>();
        OriginDto origin = new OriginDto("HAN","Noi bai","Hanoi","Vietnam");
        DestinationDto destination = new DestinationDto("SGN","SaigonArpt","SGN City","Vietnam");
        LegDto leg = new LegDto(origin,destination,120,"2024-02-15T18:40:00","2024-02-15T20:40:00",new CarrierDto(null));
        legs.add(leg);
        itinerary.setLegs(legs);


        Flight flightFromDB = Flight.builder()
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
        flightFromDB.addUser(oldTelegramUser);

        Mockito.when(flightRepository.findById(itinerary.getId())).thenReturn(Optional.of(flightFromDB));

        Flight expectedFlight = Flight.builder()
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
        expectedFlight.addUser(oldTelegramUser);
        expectedFlight.addUser(newUser);

        flightService.save(CHAT_ID,itinerary,token);

        Mockito.verify(flightRepository).findById(itinerary.getId());
        Mockito.verify(flightRepository).save(expectedFlight);

    }


}
