package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.repository.entity.Flight;

public interface FlightService {

     void save(String chatId, ItineraryDto itinerary,String token);

     Flight toEntity(ItineraryDto itinerary,String token);
}
