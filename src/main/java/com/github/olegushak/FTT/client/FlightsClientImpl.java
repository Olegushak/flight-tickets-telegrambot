package com.github.olegushak.FTT.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olegushak.FTT.dto.FlightDetailsDto;
import com.github.olegushak.FTT.dto.FlightsDto;
import com.github.olegushak.FTT.service.UniRestService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.github.olegushak.FTT.service.UniRestServiceImpl.FLIGHT_DETAILS;
import static com.github.olegushak.FTT.service.UniRestServiceImpl.JSON_KEY;
import static com.github.olegushak.FTT.service.UniRestServiceImpl.SEARCH_ONE_WAY_FORMAT;
import static com.github.olegushak.FTT.service.UniRestServiceImpl.SEARCH_ROUND_TRIP_FORMAT;

@Component
public class FlightsClientImpl implements FlightsClient{

    private final UniRestService uniRestService;

    private final ObjectMapper objectMapper;

    @Autowired
    public FlightsClientImpl(UniRestService uniRestService, ObjectMapper objectMapper) {
        this.uniRestService = uniRestService;
        this.objectMapper = objectMapper;
    }


    @Override
    public FlightsDto retrieveOneWayFlights(String fromEntityId, String toEntityId, String departDate) throws IOException {
        HttpResponse<JsonNode> response = uniRestService.get(String.format(SEARCH_ONE_WAY_FORMAT,fromEntityId,toEntityId,departDate));
        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }

        String jsonList = response.getBody().getObject().get(JSON_KEY).toString();

        return objectMapper.readValue(jsonList, new TypeReference<>() {
        });
    }

    @Override
    public FlightsDto retrieveRoundTripFlights(String fromEntityId, String toEntityId, String departDate, String returnDate) throws IOException {
        HttpResponse<JsonNode> response = uniRestService.get(String.format(SEARCH_ROUND_TRIP_FORMAT,fromEntityId,toEntityId,departDate,returnDate));
        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }

        String jsonList = response.getBody().getObject().get(JSON_KEY).toString();

        return objectMapper.readValue(jsonList, new TypeReference<>() {
        });
    }

    @Override
    public FlightDetailsDto retrieveFlightDetails(String token, String itineraryId) throws IOException{
        HttpResponse<JsonNode> response = uniRestService.get(String.format(FLIGHT_DETAILS,token,itineraryId));
        if (response.getStatus() != HttpStatus.SC_OK) {
            return null;
        }

        String jsonList = response.getBody().getObject().get(JSON_KEY).toString();

        return objectMapper.readValue(jsonList, new TypeReference<>() {
        });
    }

}
