package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class LocalisationDto {

    private String country;

    private String market;

    private String locale;

    private String currency;

    private String site;
}
