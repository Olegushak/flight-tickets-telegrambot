package com.github.olegushak.FTT.client;

import com.github.olegushak.FTT.dto.LocalisationDto;

import java.io.IOException;
import java.util.List;

public interface LocalisationClient {

    List<LocalisationDto>  retrieveLocalisations() throws IOException;
}
