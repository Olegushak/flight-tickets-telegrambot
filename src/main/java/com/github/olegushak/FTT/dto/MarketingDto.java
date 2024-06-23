package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketingDto {

    @JsonProperty("logoUrl")
    private String logoUrl;

    @JsonProperty("name")
    private String name;
}
