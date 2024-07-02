package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.WebIntegrationTest;
import com.github.olegushak.FTT.dto.AirportDto;
import kong.unirest.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@DisplayName("Integration-level testing for FttAirportsClientImplTest")
public class AirportsClientTest extends WebIntegrationTest {

    @Autowired
    private  AirportsClient airportsClient;

    @Test
    public void shouldRetrieveAirports(){
        wireMockServer.stubFor(get("/flights/airports")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("airports.json")));

        Map<String,AirportDto> airports = null;
        try {
            airports = airportsClient.retrieveAirports();
        } catch (IOException e){
            e.printStackTrace();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        //then
        Assertions.assertNotNull(airports);
        Assertions.assertFalse(airports.isEmpty());

        wireMockServer.verify(getRequestedFor(urlEqualTo("/flights/airports")));

    }
}
