package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.ItineraryDto;

public interface FlightService {

     void save(String chatId, ItineraryDto itinerary,String token);
}
