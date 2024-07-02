package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.WebIntegrationTest;
import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightDto;
import com.github.olegushak.FTT.dto.FlightRequestArgs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

@DisplayName("Integration-level testing for FttFlightsClientImplTest")
public class FlightsClientTest extends WebIntegrationTest {

    @Autowired
    private FlightsClient flightsClient;

    @Test
    public void shouldRetrieveOneWayFlights() {
        wireMockServer.stubFor(get(urlPathMatching("/flights/search-one-way?([a-z]*)"))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("oneWay.json")));

        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .fromEntityId("HAN")
                .toEntityId("SGN")
                .departDate("2024-07-17")
                .build();

        FlightDto flights = null;
        try {
            flights = flightsClient.retrieveOneWayFlights(flightRequestArgs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flights);
        Assertions.assertFalse(flights.getItineraries().isEmpty());

        wireMockServer.verify(getRequestedFor(urlPathMatching("/flights/search-one-way?([a-z]*)"))
                .withQueryParam("fromEntityId", equalTo("HAN"))
                .withQueryParam("toEntityId", equalTo("SGN"))
                .withQueryParam("departDate", equalTo("2024-07-17")));
    }

    @Test
    public void shouldRetrieveRoundTripFlights() {
        wireMockServer.stubFor(get(urlPathMatching("/flights/search-roundtrip?([a-z]*)"))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("roundTrip.json")));

        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .fromEntityId("HAN")
                .toEntityId("SGN")
                .departDate("2024-07-17")
                .returnDate("2024-07-20")
                .build();

        FlightDto flight = null;
        try {
            flight = flightsClient.retrieveRoundTripFlights(flightRequestArgs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(flight);
        Assertions.assertFalse(flight.getItineraries().isEmpty());

        wireMockServer.verify(getRequestedFor(urlPathMatching("/flights/search-roundtrip?([a-z]*)"))
                .withQueryParam("fromEntityId", equalTo("HAN"))
                .withQueryParam("toEntityId", equalTo("SGN"))
                .withQueryParam("departDate", equalTo("2024-07-17"))
                .withQueryParam("returnDate", equalTo("2024-07-20")));
    }

    @Test
    public void shouldRetrieveFlightDetails() {
        wireMockServer.stubFor(get(urlPathMatching("/flights/detail?([a-z]*)"))
                .willReturn(ok().withHeader("Content-Type", "application/json")
                        .withBodyFile("details.json")));

        FlightRequestArgs flightRequestArgs = FlightRequestArgs.builder()
                .itineraryId("18395-2407081425--31147-0-18169-2407081705")
                .token("eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiWU1RQSIsImQiOiJZSFoiLCJkMSI6IjIwMjQtMDctMDgifQ==")
                .build();

        FlightDetailsDto details = null;
        try {
            details = flightsClient.retrieveFlightDetails(flightRequestArgs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(details);
        Assertions.assertFalse(details.getItinerary().getPricingOptions().isEmpty());

        wireMockServer.verify(getRequestedFor(urlPathMatching("/flights/detail?([a-z]*)"))
                .withQueryParam("itineraryId", equalTo("18395-2407081425--31147-0-18169-2407081705"))
                .withQueryParam("token", equalTo("eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiWU1RQSIsImQiOiJZSFoiLCJkMSI6IjIwMjQtMDctMDgifQ==")));
    }
}



