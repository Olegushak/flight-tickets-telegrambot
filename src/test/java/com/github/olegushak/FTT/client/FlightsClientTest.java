package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.FlightRequestArgs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@DisplayName("Integration-level testing for FttFlightsClientImplTest")
@ActiveProfiles("test")
@SpringBootTest
public class FlightsClientTest {

    @Autowired
    private FlightsClient flightsClient;

    @Test
    @Disabled
    public void shouldRetrieveOneWayFlights(){
        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .fromEntityId("HAN")
                .toEntityId("SGN")
                .departDate("2024-07-17")
                .build();

        FlightDto flights = null;
        try {
            flights = flightsClient.retrieveOneWayFlights(flightRequestArgs);
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flights);
        Assertions.assertFalse(flights.getItineraries().isEmpty());

    }

    @Test
    @Disabled
    public void shouldRetrieveRoundTripFlights(){
        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .fromEntityId("HAN")
                .toEntityId("SGN")
                .departDate("2024-07-17")
                .returnDate("2024-07-20")
                .build();

        FlightDto flight = null;
        try {
            flight = flightsClient.retrieveRoundTripFlights(flightRequestArgs);
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flight);
        Assertions.assertFalse(flight.getItineraries().isEmpty());

    }

    @Test
    @Disabled
    public void shouldRetrieveFlightDetails(){
        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .itineraryId("16240-2406212045--31703-0-12071-2406212250")
                .token("eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiU0dOIiwiZCI6IkhBTiIsImQxIjoiMjAyNC0wNi0yMSJ9")
                .build();

        FlightDetailsDto details = null;
        try {
            details = flightsClient.retrieveFlightDetails(flightRequestArgs);
        } catch (IOException e){
            e.printStackTrace();
        }

        Assertions.assertNotNull(details);
        Assertions.assertFalse(details.getItinerary().getPricingOptions().isEmpty());

    }

}



