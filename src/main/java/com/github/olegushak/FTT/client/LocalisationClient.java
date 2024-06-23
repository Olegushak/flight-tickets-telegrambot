package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.LocalisationDto;

import java.io.IOException;
import java.util.Map;

public interface LocalisationClient {

    Map<String, LocalisationDto> retrieveLocalisations() throws IOException;
}
