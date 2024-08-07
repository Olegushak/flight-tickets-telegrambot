package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.repository.entity.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    void saveAll(List<AirportDto> airports);

    Optional<Airport> findAirportByLocation(String name);

    void deleteAll();

    List<String> search(String airportName);

}