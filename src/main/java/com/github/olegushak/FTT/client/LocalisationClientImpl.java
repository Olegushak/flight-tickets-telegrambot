package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.dto.ResponseDto;
import kong.unirest.GenericType;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocalisationClientImpl implements LocalisationClient{

    private final UnirestInstance unirest;
    private final String LOCALISATION_FORMAT = "/get-config";
    @Value("${rapid.api.url}")
    private String URL;

    @Autowired
    public LocalisationClientImpl(UnirestInstance unirest) {
        this.unirest = unirest;
    }

    @Override
    public Map<String,LocalisationDto> retrieveLocalisations(){
        ResponseDto<LocalisationDto> response = unirest.get(URL + LOCALISATION_FORMAT)
                .asObject(new GenericType<ResponseDto<LocalisationDto>>() {
                })
                .getBody();

        return response.getData().stream()
                .collect(Collectors.toMap(LocalisationDto::getCountry, localisation -> localisation));
    }
}
