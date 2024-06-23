package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class LegDto {

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
