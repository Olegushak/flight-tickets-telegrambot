package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocalisationDto {

    @JsonProperty("country")
    private String country;

    @JsonProperty("market")
    private String market;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("currencyTitle")
    private String currencyTitle;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("currencySymbol")
    private String currencySymbol;

    @JsonProperty("site")
    private String site;
}
