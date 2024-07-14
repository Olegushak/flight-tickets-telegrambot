package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.repository.LocalisationRepository;
import com.github.olegushak.FTT.repository.entity.Localisation;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalisationServiceImpl implements LocalisationService{

    private final LocalisationRepository localisationRepository;

    private final DtoMapper dtoMapper;

    @Autowired
    public LocalisationServiceImpl(LocalisationRepository localisationRepository, DtoMapper dtoMapper) {
        this.localisationRepository = localisationRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void saveAll(List<LocalisationDto> localisations) {
        localisations.stream().map(dtoMapper::localisationDtoToEntity).forEach(localisationRepository::save);
    }

    @Override
    public Optional<Localisation> findLocalisationByCountry(String country) {
        return Optional.of(localisationRepository.getLocalisationByCountry(country).get());
    }

    @Override
    public void deleteAll() {
       localisationRepository.deleteAll();
    }


}
