package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.WebIntegrationTest;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightRepositoryIT extends WebIntegrationTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @BeforeEach
    public void beforeEach(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldProperlyGetAllUsersForFlight(){

        Flight flight = new Flight("15083-2402151840--30858,-31825-1-14355-2402160020","HAN","Vietnam","Hanoi","SGN","Vietnam","Ho Chi Minh City","2024-02-15T18:40:00","2024-02-15T20:40:00",120,"$600","eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiUEFSSSIsImQiOiJNU1lBIiwiZDEiOiIyMDI0LTAyLTE1In0=",new ArrayList<>());

        TelegramUser user = new TelegramUser();
        user.setChatId("123456789");
        user.setActive(true);
        user.setFlights(new ArrayList<>());

        TelegramUser user1 = new TelegramUser();
        user1.setChatId("123456780");
        user1.setActive(true);
        user1.setFlights(new ArrayList<>());

        flight.addUser(user);
        flight.addUser(user1);

        telegramUserRepository.save(user);
        telegramUserRepository.save(user1);
        flightRepository.save(flight);


        Optional<Flight> expectedFlight = flightRepository.findById("15083-2402151840--30858,-31825-1-14355-2402160020");

        Assertions.assertTrue(expectedFlight.isPresent());
        Assertions.assertEquals("15083-2402151840--30858,-31825-1-14355-2402160020",expectedFlight.get().getId());
        List<TelegramUser> telegramUsers = expectedFlight.get().getUsers();
        Assertions.assertEquals("123456789",telegramUsers.get(1).getChatId());
        Assertions.assertTrue(telegramUsers.get(0).isActive());
        Assertions.assertEquals(2, telegramUsers.size());
    }
}
