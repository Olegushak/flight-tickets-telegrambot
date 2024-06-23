package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.dto.ResponseDto;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;


@Component
public class AirportsClientImpl implements AirportsClient {

    private final UnirestInstance unirest;
    public static final String AIRPORTS_FORMAT = "/flights/airports";
    private static final String HOST = "https://sky-scanner3.p.rapidapi.com";

    @Autowired
    public AirportsClientImpl(UnirestInstance unirest){
        this.unirest = unirest;
    }

    @Override
    public Map<String, AirportDto> retrieveAirports()  {
        ResponseDto response = unirest.get(HOST + AIRPORTS_FORMAT)
                .asObject(ResponseDto.class)
                .getBody();

        return response.getData().stream()
                .map(airport -> (AirportDto) airport).filter(airport -> airport.getSkyId()!= null)
                .collect(Collectors.toMap(AirportDto::getName, airport -> airport));
    }
}
