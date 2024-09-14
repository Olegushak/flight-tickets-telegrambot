package com.github.olegushak.FTT.repository.entity;

import com.github.olegushak.FTT.dto.LegDto;
import com.github.olegushak.FTT.dto.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class FlightReviewEntity {

    private String id;

    private List<LegDto> legs;

    private PriceDto price;

    private String token;
}
