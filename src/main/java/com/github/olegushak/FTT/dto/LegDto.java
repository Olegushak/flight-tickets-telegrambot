package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("origin")
    private OriginDto origin;

    @JsonProperty("destination")
    private DestinationDto destination;

    @JsonProperty("durationInMinutes")
    private int durationInMinutes;

    @JsonProperty("departure")
    private String departure;

    @JsonProperty("arrival")
    private String arrival;

    @JsonProperty("carriers")
    private CarrierDto carriers;




}
