package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.DetailsResponseDto;
import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.FlightRequestArgs;
import com.github.olegushak.FTT.dto.FlightResponseDto;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightsClientImpl implements FlightsClient{

    private final UnirestInstance unirest;
    public static final String SEARCH_ONE_WAY_FORMAT = "/flights/search-one-way";
    public static final String SEARCH_ROUND_TRIP_FORMAT = "/flights/search-roundtrip";
    public static final String FLIGHT_DETAILS = "/flights/detail";

    private static final String HOST = "https://sky-scanner3.p.rapidapi.com";

    @Autowired
    public FlightsClientImpl(UnirestInstance unirest){
        this.unirest = unirest;
    }

    @Override
    public FlightDto retrieveOneWayFlights(FlightRequestArgs flightRequestArgs)  {
        FlightResponseDto response = unirest.get(HOST + SEARCH_ONE_WAY_FORMAT)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(FlightResponseDto.class)
                .getBody();
        return response.getData();
    }

    @Override
    public FlightDto retrieveRoundTripFlights(FlightRequestArgs flightRequestArgs) {
        FlightResponseDto response = unirest.get(HOST + SEARCH_ROUND_TRIP_FORMAT)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(FlightResponseDto.class)
                .getBody();

        return response.getData();
    }

    @Override
    public FlightDetailsDto retrieveFlightDetails(FlightRequestArgs flightRequestArgs) {
        DetailsResponseDto response = unirest.get(HOST + FLIGHT_DETAILS)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(DetailsResponseDto.class)
                .getBody();
        return response.getData();
    }

}
