package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;
import kong.unirest.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Map;

@DisplayName("Integration-level testing for FttAirportsClientImplTest")
@ActiveProfiles("test")
@SpringBootTest
public class AirportsClientTest {

    @Autowired
    private  AirportsClient airportsClient;

    @Test
    public void shouldRetrieveAirports(){
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
    }
}
