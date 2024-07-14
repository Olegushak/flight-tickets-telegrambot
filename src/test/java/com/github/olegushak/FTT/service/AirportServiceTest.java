package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.AirportDto;

import com.github.olegushak.FTT.repository.AirportRepository;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

@DisplayName("Unit-level testing for AirportService")
public class AirportServiceTest {

    private AirportService airportService;

    private AirportRepository airportRepository;

    private final DtoMapper dtoMapper = new DtoMapper();

    @BeforeEach
    public void init(){
        airportRepository = Mockito.mock(AirportRepository.class);
        airportService = new AirportServiceImpl(airportRepository, dtoMapper);
    }

    @Test
    public void shouldProperlySaveAllAirports(){

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

        List<AirportDto> airports = List.of(airportDto1,airportDto2);

        airportService.saveAll(airports);

        Airport expectedLAirport1 = Airport.builder()
                .iata(airportDto1.getIata())
                .icao(airportDto1.getIcao())
                .name(airportDto1.getName())
                .location(airportDto1.getLocation())
                .time(airportDto1.getTime())
                .skyId(airportDto1.getSkyId())
                .build();

        Airport expectedLAirport2 = Airport.builder()
                .iata(airportDto2.getIata())
                .icao(airportDto2.getIcao())
                .name(airportDto2.getName())
                .location(airportDto2.getLocation())
                .time(airportDto2.getTime())
                .skyId(airportDto2.getSkyId())
                .build();



        Mockito.verify(airportRepository).save(expectedLAirport1);
        Mockito.verify(airportRepository).save(expectedLAirport2);

    }
}
