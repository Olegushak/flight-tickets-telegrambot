package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.DetailedItineraryDto;
import com.github.olegushak.FTT.repository.entity.FlightReviewEntity;
import com.github.olegushak.FTT.utils.FlightRequestArgs;

import java.util.Map;

public interface FlightService {

     void saveTicket(String chatId, FlightReviewEntity flightReviewEntity);

     Map<String, FlightReviewEntity> roundTripSearch(FlightRequestArgs flightRequestArgs);

     DetailedItineraryDto getFlightDetails(FlightRequestArgs flightRequestArgs);
}
