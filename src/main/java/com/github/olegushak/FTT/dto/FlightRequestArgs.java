package com.github.olegushak.FTT.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Builder
@Getter
public class FlightRequestArgs {

    private final String fromEntityId;
    private final String toEntityId;
    private final String departDate;
    private final String returnDate;
    private final String token;
    private final String itineraryId;
    private final String market;
    private final String locale;
    private final String currency;

    public Map<String, Object> populateQueries(){

        Map<String,Object> queries = new HashMap<>();
        if(nonNull(fromEntityId)) {
            queries.put("fromEntityId", fromEntityId);
        }
        if(nonNull(toEntityId)) {
            queries.put("toEntityId", toEntityId);
        }
        if(nonNull(departDate)) {
            queries.put("departDate", departDate);
        }
        if(nonNull(returnDate)) {
            queries.put("returnDate", returnDate);
        }
        if(nonNull(market)) {
            queries.put("market", returnDate);
        }
        if(nonNull(locale)) {
            queries.put("locale",locale);
        }
        if(nonNull(currency)) {
            queries.put("currency",currency);
        }
        if(nonNull(token)) {
            queries.put("token", token);
        }
        if(nonNull(itineraryId)) {
            queries.put("itineraryId", itineraryId);
        }
        return queries;
    }
}
