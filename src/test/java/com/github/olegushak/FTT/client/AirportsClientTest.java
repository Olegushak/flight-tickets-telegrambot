package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

@DisplayName("Integration-level testing for FttAirportsClientImplTest")
@ActiveProfiles("test")
@SpringBootTest
public class AirportsClientTest {

    @Autowired
    private  AirportsClient airportsClient;

    @Test
    public void shouldRetrieveAirports(){
        List<AirportDto> airportsList = null;
        try {
            airportsList = airportsClient.retrieveAirports();
        } catch (IOException e){
            e.printStackTrace();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        //then
        Assertions.assertNotNull(airportsList);
        Assertions.assertFalse(airportsList.isEmpty());
    }
}
