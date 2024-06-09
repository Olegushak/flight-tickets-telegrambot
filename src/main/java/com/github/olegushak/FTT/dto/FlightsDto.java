package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightsDto {

    @JsonProperty("itineraries")
    private List<ItineraryDto> itineraries;

    @JsonProperty("token")
    private String token;

}
