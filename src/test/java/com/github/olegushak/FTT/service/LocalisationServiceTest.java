package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.repository.LocalisationRepository;
import com.github.olegushak.FTT.repository.entity.Localisation;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

@DisplayName("Unit-level testing for LocalisationService")
public class LocalisationServiceTest{

    private  LocalisationService localisationService;

    private LocalisationRepository localisationRepository;

    private final DtoMapper dtoMapper = new DtoMapper();

    @BeforeEach
    public void init(){
        localisationRepository = Mockito.mock(LocalisationRepository.class);
        localisationService = new LocalisationServiceImpl(localisationRepository, dtoMapper);

    }

    @Test
    public void shouldProperlySaveAllLocalisations(){
        LocalisationDto localisationDto = new LocalisationDto();
        localisationDto.setCountry("United Arab Emirates");
        localisationDto.setMarket("AE");
        localisationDto.setLocale("en-US");
        localisationDto.setCurrency("AED");
        localisationDto.setSite("www.skyscanner.ae");

        LocalisationDto localisationDto1 = new LocalisationDto();
        localisationDto1.setCountry("Morocco");
        localisationDto1.setMarket("MA");
        localisationDto1.setLocale("fr-FR");
        localisationDto1.setCurrency("EUR");
        localisationDto1.setSite("www.skyscanner.fr");

        List<LocalisationDto> localisations = List.of(localisationDto,localisationDto1);

        localisationService.saveAll(localisations);

        Localisation expectedLocalisation = Localisation.builder()
                .country(localisationDto.getCountry())
                .market(localisationDto.getMarket())
                .locale(localisationDto.getLocale())
                .currency(localisationDto.getCurrency())
                .site(localisationDto.getSite())
                .build();

        Localisation expectedLocalisation1 = Localisation.builder()
                .country(localisationDto1.getCountry())
                .market(localisationDto1.getMarket())
                .locale(localisationDto1.getLocale())
                .currency(localisationDto1.getCurrency())
                .site(localisationDto1.getSite())
                .build();

        Mockito.verify(localisationRepository).save(expectedLocalisation);
        Mockito.verify(localisationRepository).save(expectedLocalisation1);

    }




}
