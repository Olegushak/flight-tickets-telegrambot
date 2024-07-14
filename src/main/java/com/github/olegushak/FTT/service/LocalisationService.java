package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.repository.entity.Localisation;

import java.util.List;
import java.util.Optional;

public interface LocalisationService {

    void saveAll(List<LocalisationDto> localisations);

    Optional<Localisation> findLocalisationByCountry(String country);

    void deleteAll();
}
