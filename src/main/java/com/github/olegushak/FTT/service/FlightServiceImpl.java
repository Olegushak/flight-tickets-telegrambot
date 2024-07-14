package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.dto.ItineraryDto;
import com.github.olegushak.FTT.repository.FlightRepository;
import com.github.olegushak.FTT.repository.entity.Flight;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import com.github.olegushak.FTT.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final TelegramUserService telegramUserService;

    private final DtoMapper dtoMapper;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, TelegramUserService telegramUserService, DtoMapper dtoMapper) {
        this.flightRepository = flightRepository;
        this.telegramUserService = telegramUserService;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void save(String chatId, ItineraryDto itinerary,String token) {
        TelegramUser user = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);

        Flight flightToSave;
        Optional<Flight> flightFromDB = flightRepository.findById(itinerary.getId());
        if(flightFromDB.isPresent()) {
            flightToSave = flightFromDB.get();
            Optional<TelegramUser> first = flightToSave.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                flightToSave.addUser(user);
            }
        } else {
            flightToSave = dtoMapper.flightDtoToEntity(itinerary,token);
            flightToSave.addUser(user);
        }
        flightRepository.save(flightToSave);
    }
}
