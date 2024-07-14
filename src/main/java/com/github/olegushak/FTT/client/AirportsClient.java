package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.AirportDto;


import java.io.IOException;
import java.util.List;

public interface AirportsClient {
    List<AirportDto> retrieveAirports ()
            throws IOException;
}
