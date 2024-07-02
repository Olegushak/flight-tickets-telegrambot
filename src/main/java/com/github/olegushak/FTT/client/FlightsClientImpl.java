package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.FlightRequestArgs;
import com.github.olegushak.FTT.dto.FlightResponseDto;
import kong.unirest.GenericType;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlightsClientImpl implements FlightsClient{
    private final UnirestInstance unirest;
    public static final String SEARCH_ONE_WAY_FORMAT = "/flights/search-one-way";
    public static final String SEARCH_ROUND_TRIP_FORMAT = "/flights/search-roundtrip";
    public static final String FLIGHT_DETAILS = "/flights/detail";

    @Value("${rapid.api.url}")
    private String URL;

    @Autowired
    public FlightsClientImpl(UnirestInstance unirest){
        this.unirest = unirest;
    }

    @Override
    public FlightDto retrieveOneWayFlights(FlightRequestArgs flightRequestArgs)  {
        FlightResponseDto<FlightDto> response = unirest.get(URL + SEARCH_ONE_WAY_FORMAT)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(new GenericType<FlightResponseDto<FlightDto>>() {
                })
                .getBody();
        return response.getData();
    }

    @Override
    public FlightDto retrieveRoundTripFlights(FlightRequestArgs flightRequestArgs) {
        FlightResponseDto<FlightDto> response = unirest.get(URL + SEARCH_ROUND_TRIP_FORMAT)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(new GenericType<FlightResponseDto<FlightDto>>() {
                })
                .getBody();

        return response.getData();
    }

    @Override
    public FlightDetailsDto retrieveFlightDetails(FlightRequestArgs flightRequestArgs) {
        FlightResponseDto<FlightDetailsDto> response = unirest.get(URL + FLIGHT_DETAILS)
                .queryString(flightRequestArgs.populateQueries())
                .asObject(new GenericType<FlightResponseDto<FlightDetailsDto> >() {
                })
                .getBody();
        return response.getData();
    }

}
