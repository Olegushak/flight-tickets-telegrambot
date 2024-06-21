package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.FlightRequestArgs;


import java.io.IOException;

public interface FlightsClient {

    FlightDto retrieveOneWayFlights(FlightRequestArgs flightRequestArgs) throws IOException;

    FlightDto retrieveRoundTripFlights(FlightRequestArgs flightRequestArgs) throws IOException;

    FlightDetailsDto retrieveFlightDetails(FlightRequestArgs flightRequestArgs) throws IOException;



}
