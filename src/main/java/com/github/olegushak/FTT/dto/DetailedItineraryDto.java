package com.github.olegushak.FTT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedItineraryDto {

    private List<PricingOptionsDto> pricingOptions;

    private boolean isTransferRequired;

    private String destinationImage;
}
