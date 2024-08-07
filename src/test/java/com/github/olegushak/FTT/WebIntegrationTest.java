package com.github.olegushak.FTT;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.starter.TelegramBotInitializer;

@SpringBootTest( classes = FlightTicketsTelegrambotApplication.class)
public class WebIntegrationTest {

    @MockBean
    TelegramBotInitializer telegramBotInitializer;

    public static final WireMockServer wireMockServer = new WireMockServer(8181);

    @BeforeAll
    static void startRecording() {
        wireMockServer.start();
    }

    @AfterAll
    static void stopRecording(){
        wireMockServer.stop();
    }
}
