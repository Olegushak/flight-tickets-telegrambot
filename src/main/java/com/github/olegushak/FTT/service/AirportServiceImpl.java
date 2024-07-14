package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.repository.AirportRepository;
import com.github.olegushak.FTT.repository.entity.Airport;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService{

    private final AirportRepository airportRepository;

    private final DtoMapper dtoMapper;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository, DtoMapper dtoMapper) {
        this.airportRepository = airportRepository;
        this.dtoMapper = dtoMapper;
    }


    @Override
    public void saveAll(List<AirportDto> airports) {
        airports.stream().map(dtoMapper::airportDtoToEntity).forEach(airportRepository::save);

    }

    @Override
    public Optional<Airport> findAirportByName(String name) {
        return Optional.of(airportRepository.getAirportByName(name).get());
    }

    @Override
    public void deleteAll() {
       airportRepository.deleteAll();
    }
}
