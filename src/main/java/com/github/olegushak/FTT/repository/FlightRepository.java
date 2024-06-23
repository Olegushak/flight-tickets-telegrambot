package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.repository.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight,String> {

}
