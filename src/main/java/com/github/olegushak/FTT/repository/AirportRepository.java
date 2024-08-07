package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.repository.entity.Airport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends ElasticsearchRepository<Airport, String> {

    Optional<Airport> getAirportByLocationContaining(String city);
}
