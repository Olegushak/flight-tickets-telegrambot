package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightsDto;

import java.io.IOException;

public interface FlightsClient {

    FlightsDto retrieveOneWayFlights(String fromEntityId, String toEntityId, String departDate) throws IOException;

    FlightsDto retrieveRoundTripFlights(String fromEntityId, String toEntityId, String departDate, String returnDate) throws IOException;

    FlightDetailsDto retrieveFlightDetails(String token, String itineraryId) throws IOException;



}
