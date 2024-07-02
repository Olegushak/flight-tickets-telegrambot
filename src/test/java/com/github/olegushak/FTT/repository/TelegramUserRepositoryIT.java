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


public class TelegramUserRepositoryIT extends WebIntegrationTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    public void beforeEach(){
        telegramUserRepository.deleteAll();
        flightRepository.deleteAll();
    }

    @Test
    public void  shouldProperlyFindAllActiveUsers() {

      for (int i = 0; i < 10; i++){
          TelegramUser user = new TelegramUser();
          user.setChatId(String.valueOf(123456781 + i));
          user.setActive(i % 2 == 0);
          telegramUserRepository.save(user);
      }
      
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        Assertions.assertEquals(5,users.size());
    }

    @Test
    public void shouldProperlySaveTelegramUser(){

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("123456789");
        telegramUser.setActive(false);
        telegramUser.setFlights(new ArrayList<>());
        telegramUserRepository.save(telegramUser);

        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser,saved.get());

    }

    @Test
    public void shouldProperlyGetAllFlightsForUser(){
        TelegramUser user = new TelegramUser();
        user.setChatId("123456789");
        user.setActive(true);
        user.setFlights(new ArrayList<>());

        Flight flight = new Flight("15083-2402151840--30858,-31825-1-14355-2402160020","HAN","Vietnam","Hanoi","SGN","Vietnam","Ho Chi Minh City","2024-02-15T18:40:00","2024-02-15T20:40:00",120,"$600","eyJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiUEFSSSIsImQiOiJNU1lBIiwiZDEiOiIyMDI0LTAyLTE1In0=",new ArrayList<>());
        flight.addUser(user);
        Flight flight1 = new Flight("15083-2402191150--31058,-31821-1-14666-2402160908","HAN","Vietnam","Hanoi","KJA","Russia","Krasnoyarsk","2024-02-18T18:30:00","2024-06-18T21:30:00",180,"$300","ImJhIjoxLCJjIjowLCJpIjowLCJjYyI6ImVjb25vbXkiLCJvIjoiUEFSSSIsImQiOiJNU1lBIiwiZDEiOiIyMDI0AHAyLTE1In0=",new ArrayList<>());
        flight1.addUser(user);

        telegramUserRepository.save(user);
        flightRepository.save(flight);
        flightRepository.save(flight1);

        Optional<TelegramUser> userFromDB = telegramUserRepository.findById("123456789");

        Assertions.assertTrue(userFromDB.isPresent());
        List<Flight> flights = userFromDB.get().getFlights();

        Assertions.assertEquals("HAN", flights.get(0).getDeparture());
        Assertions.assertEquals("KJA", flights.get(1).getDestination());
        Assertions.assertEquals("15083-2402151840--30858,-31825-1-14355-2402160020", flights.get(0).getId());
        Assertions.assertEquals("15083-2402191150--31058,-31821-1-14666-2402160908", flights.get(1).getId());
    }
}
