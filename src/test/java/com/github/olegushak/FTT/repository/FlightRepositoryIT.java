package com.github.olegushak.FTT.repository;

import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= NONE)
public class FlightRepositoryIT {

    @Autowired
    private FlightRepository flightRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/twoUsersForFlight.sql"})
    @Test
    public void shouldProperlyGetAllUsersForFlight(){

        Optional<Flight> flight = flightRepository.findById("15083-2402151840--30858,-31825-1-14355-2402160020");

        Assertions.assertTrue(flight.isPresent());
        Assertions.assertEquals("15083-2402151840--30858,-31825-1-14355-2402160020",flight.get().getId());
        List<TelegramUser> telegramUsers = flight.get().getUsers();
        Assertions.assertEquals("123456789",telegramUsers.get(0).getChatId());
        Assertions.assertTrue(telegramUsers.get(1).isActive());
        Assertions.assertEquals(2, telegramUsers.size());

    }
}
