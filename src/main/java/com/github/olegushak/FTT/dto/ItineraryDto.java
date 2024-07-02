package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItineraryDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("price")
    private PriceDto price;

    @JsonProperty("legs")
    private List<LegDto> legs;


}
