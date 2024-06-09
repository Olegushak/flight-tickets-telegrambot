package com.github.olegushak.FTT.client;
import com.github.olegushak.FTT.dto.LocalisationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

@DisplayName("Integration-level testing for FttLocalisationClientImplTest")
@ActiveProfiles("test")
@SpringBootTest
public class LocalisationClientTest {

   @Autowired
    private LocalisationClient localisationClient;

    @Test
    public void shouldRetrieveLocalisations(){
        List<LocalisationDto> localisationList = null;
        try {
            localisationList = localisationClient.retrieveLocalisations();
        } catch (IOException e){
            e.printStackTrace();
        }

        //then
        Assertions.assertNotNull(localisationList);
        Assertions.assertFalse(localisationList.isEmpty());
    }

}
