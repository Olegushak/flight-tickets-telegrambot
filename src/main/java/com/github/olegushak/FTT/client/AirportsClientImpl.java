package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;
import com.github.olegushak.FTT.dto.ResponseDto;
import kong.unirest.GenericType;
import kong.unirest.UnirestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportsClientImpl implements AirportsClient {
    private final UnirestInstance unirest;
    private static final String AIRPORTS_FORMAT = "/flights/airports";

    @Value("${rapid.api.url}")
    private String URL;

    @Autowired
    public AirportsClientImpl(UnirestInstance unirest){
        this.unirest = unirest;
    }

    @Override
    public List<AirportDto> retrieveAirports()  {
        ResponseDto<AirportDto> response = unirest.get(URL + AIRPORTS_FORMAT)
                .asObject(new GenericType<ResponseDto<AirportDto>>() {
                })
                .getBody();

        return response.getData().stream()
                .filter(airport -> airport.getSkyId()!= null)
                .collect(Collectors.toList());
    }
}
