package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString //TODO for what? @Data does include @ToString ?
@JsonIgnoreProperties //TODO does it work? (ignoreUnknown=true)?
public class AirportDto {

    @JsonProperty("iata")
    private String iata;

    @JsonProperty("icao")
    private String icao;

    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;

    @JsonProperty("time")
    private String time;

    @JsonProperty("skyId")
    private String skyId;
}
