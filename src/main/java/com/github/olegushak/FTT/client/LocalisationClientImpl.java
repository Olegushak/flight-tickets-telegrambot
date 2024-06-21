package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.dto.ResponseDto;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocalisationClientImpl implements LocalisationClient{

    private final UnirestInstance unirest;
    private final String LOCALISATION_FORMAT = "/get-config";
    public final String HOST = "https://sky-scanner3.p.rapidapi.com";

    @Autowired
    public LocalisationClientImpl(UnirestInstance unirest) {
        this.unirest = unirest;
    }

    @Override
    public Map<String,LocalisationDto> retrieveLocalisations(){
        ResponseDto response = unirest.get(HOST + LOCALISATION_FORMAT)
                .asObject(ResponseDto.class)
                .getBody();

        return response.getData().stream()
                .map(localisation -> (LocalisationDto)localisation )
                .collect(Collectors.toMap(LocalisationDto::getCountry, localisation -> localisation));
    }
}
