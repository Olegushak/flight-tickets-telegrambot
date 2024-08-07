package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.WebIntegrationTest;
import com.github.olegushak.FTT.dto.AirportDto;

import com.github.olegushak.FTT.repository.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

@DisplayName("Unit-level testing for AirportService")
public class AirportServiceTest extends WebIntegrationTest {

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportRepository airportRepository;

    @BeforeEach
    public void init(){
      airportRepository.deleteAll();
    }

    @Test
    public void shouldProperlyFindAirportsByCity(){

        String expectedCity = "Anaa";
        String request = "An";

        AirportDto airportDto1 = new AirportDto();
        airportDto1.setIata("AAA");
        airportDto1.setIcao("NTGA");
        airportDto1.setName("Anaa Airport");
        airportDto1.setLocation("Anaa, Tuamotus, French Polynesia");
        airportDto1.setTime("UTC−10:00");
        airportDto1.setSkyId("AAA");

        AirportDto airportDto2 = new AirportDto();
        airportDto2.setIata("ABQ");
        airportDto2.setIcao("KABQ");
        airportDto2.setName("Albuquerque International Sunport");
        airportDto2.setLocation("Albuquerque, New Mexico, United States");
        airportDto2.setTime("UTC−07:00");
        airportDto2.setSkyId("ABQ");

        List<AirportDto> airports = new ArrayList<>();
        airports.add(airportDto1);
        airports.add(airportDto2);

        airportService.saveAll(airports);

        List<String> expectedAirports = airportService.search(request);

        Assertions.assertFalse(expectedAirports.isEmpty());
        Assertions.assertTrue(expectedAirports.get(0).contains(expectedCity));

    }
}
