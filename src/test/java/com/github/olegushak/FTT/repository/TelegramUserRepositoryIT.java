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
public class TelegramUserRepositoryIT {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    public void  shouldProperlyFindAllActiveUsers() {
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        Assertions.assertEquals(5,users.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveTelegramUser(){

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("123456789");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser,saved.get());

    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/twoFlightsForUser.sql"})
    @Test
    public void shouldProperlyGetAllFlightsForUser(){
        Optional<TelegramUser> userFromDB = telegramUserRepository.findById("123456789");

        Assertions.assertTrue(userFromDB.isPresent());
        List<Flight> flights = userFromDB.get().getFlights();

        Assertions.assertEquals("HAN", flights.get(0).getDeparture());
       Assertions.assertEquals("KJA", flights.get(1).getDestination());
        Assertions.assertEquals("15083-2402151840--30858,-31825-1-14355-2402160020", flights.get(0).getId());
        Assertions.assertEquals("15083-2402191150--31058,-31821-1-14666-2402160908", flights.get(1).getId());
    }
}
