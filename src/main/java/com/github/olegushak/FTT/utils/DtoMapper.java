package com.github.olegushak.FTT.utils;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.repository.entity.Localisation;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public FlightReviewEntity flightDtoToEntity(ItineraryDto itinerary, String token) {
        return FlightReviewEntity.builder()
                .id(itinerary.getId())
                .legs(itinerary.getLegs())
                .token(token)
                .price(itinerary.getPrice())
                .build();
    }

    public Localisation localisationDtoToEntity(LocalisationDto localisation) {
        return Localisation.builder()
                .country(localisation.getCountry())
                .market(localisation.getMarket())
                .locale(localisation.getLocale())
                .currency(localisation.getCurrency())
                .site(localisation.getSite())
                .build();
    }

    public Airport airportDtoToEntity(AirportDto airportDto) {
        return Airport.builder()
                .iata(airportDto.getIata())
                .icao(airportDto.getIcao())
                .name(airportDto.getName())
                .location(airportDto.getLocation())
                .time(airportDto.getTime())
                .skyId(airportDto.getSkyId()).build();
    }


}
