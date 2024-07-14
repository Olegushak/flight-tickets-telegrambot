package com.github.olegushak.FTT.client;
import com.github.olegushak.FTT.WebIntegrationTest;
import com.github.olegushak.FTT.dto.LocalisationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@DisplayName("Integration-level testing for FttLocalisationClientImplTest")
public class LocalisationClientTest extends WebIntegrationTest {

   @Autowired
    private LocalisationClient localisationClient;

    @Test
    public void shouldRetrieveLocalisations(){
        wireMockServer.stubFor(get("/get-config")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("sc_config.json")));

        List<LocalisationDto> localisations = null;
        try {
            localisations = localisationClient.retrieveLocalisations();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(localisations);
        Assertions.assertFalse(localisations.isEmpty());

        wireMockServer.verify(getRequestedFor(urlEqualTo("/get-config")));
    }

}
