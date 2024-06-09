package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.exception.FlightClientException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UniRestServiceImpl implements UniRestService{

    public static final String HOST = "https://sky-scanner3.p.rapidapi.com";

    public static final String LOCALISATION_FORMAT = "/get-config";
    public static final String AIRPORTS_FORMAT = "/flights/airports";
    public static final String SEARCH_ONE_WAY_FORMAT = "/flights/search-one-way?fromEntityId=%s&toEntityId=%S&departDate=%s";
    public static final String SEARCH_ROUND_TRIP_FORMAT = "/flights/search-one-way?fromEntityId=%s&toEntityId=%S&departDate=%s&returnDate=$s";
    public static final String FLIGHT_DETAILS = "/flights/detail?token=%s&itineraryId=%s";
    public static final String JSON_KEY = "data";

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Override
    public HttpResponse<JsonNode> get(String path) {

        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(HOST + path)
                    .header("X-RapidAPI-Host", "sky-scanner3.p.rapidapi.com")
                    .header("X-RapidAPI-Key", rapidApiKey)
                    .asJson();
        } catch (UnirestException e) {
            throw new FlightClientException(String.format("Request failed, path=%s", HOST + path), e);
        }

        return response;

    }

}
