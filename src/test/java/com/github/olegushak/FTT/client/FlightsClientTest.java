package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightsDto;
import org.junit.jupiter.api.Assertions;
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
    public void shouldRetrieveOneWayFlights(){
        FlightsDto flights = null;
        try {
            flights = flightsClient.retrieveOneWayFlights("HAN","SGN","2024-07-17");
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flights);
        Assertions.assertFalse(flights.getItineraries().isEmpty());

    }

    @Test
    public void shouldRetrieveRoundTripFlights(){
        FlightsDto flights = null;
        try {
            flights = flightsClient.retrieveRoundTripFlights("HAN","SGN","2024-07-17","2024-07-20");
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flights);
        Assertions.assertFalse(flights.getItineraries().isEmpty());

    }

    @Test
    public void shouldRetrieveFlightDetails(){
        FlightDetailsDto details = null;
        try {
            details = flightsClient.retrieveFlightDetails("eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiU0dOIiwiZCI6IkhBTiIsImQxIjoiMjAyNC0wNi0yMSJ9","16240-2406212045--31703-0-12071-2406212250");
        } catch (IOException e){
            e.printStackTrace();
        }

        Assertions.assertNotNull(details);
        Assertions.assertFalse(details.getItinerary().getPricingOptions().isEmpty());

    }

}



