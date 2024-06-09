package com.github.olegushak.FTT.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olegushak.FTT.dto.LocalisationDto;
import com.github.olegushak.FTT.service.UniRestService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;

import static com.github.olegushak.FTT.service.UniRestServiceImpl.JSON_KEY;
import static com.github.olegushak.FTT.service.UniRestServiceImpl.LOCALISATION_FORMAT;


@Component
public class LocalisationClientImpl implements LocalisationClient{

    private final UniRestService uniRestService;


    private final ObjectMapper objectMapper ;

    @Autowired
    public LocalisationClientImpl(UniRestService uniRestService, ObjectMapper objectMapper) {
        this.uniRestService = uniRestService;
        this.objectMapper = objectMapper;
    }


    @Override
    public List<LocalisationDto> retrieveLocalisations() throws IOException{

       HttpResponse<JsonNode> response = uniRestService.get(LOCALISATION_FORMAT);
        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }

        String jsonList = response.getBody().getObject().get(JSON_KEY).toString();

       return objectMapper.readValue(jsonList, new TypeReference<>() {
       });
    }
}