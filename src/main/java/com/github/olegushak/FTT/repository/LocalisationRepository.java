package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.repository.entity.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalisationRepository extends JpaRepository<Localisation,Long> {

    Optional<Localisation> getLocalisationByCountry(String country);

}
