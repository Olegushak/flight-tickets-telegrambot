package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;


import java.io.IOException;
import java.util.Map;

public interface AirportsClient {
    Map<String, AirportDto> retrieveAirports ()
            throws IOException;
}
