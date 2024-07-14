package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.repository.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport,Long> {

    Optional<Airport> getAirportByName(String name);
}
