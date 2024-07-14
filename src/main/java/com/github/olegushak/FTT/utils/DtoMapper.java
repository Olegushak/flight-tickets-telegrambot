package com.github.olegushak.FTT.utils;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.dto.DestinationDto;
import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.dto.OriginDto;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.Localisation;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public Flight flightDtoToEntity(ItineraryDto itinerary, String token) {
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
