package com.github.olegushak.FTT.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Getter
@Setter
@NoArgsConstructor()
public class FlightRequestArgs {

    private String fromEntityId;
    private String toEntityId;
    private String departDate;
    private String returnDate;
    private String token;
    private String itineraryId;
    private String market;
    private String locale;
    private String currency;

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
