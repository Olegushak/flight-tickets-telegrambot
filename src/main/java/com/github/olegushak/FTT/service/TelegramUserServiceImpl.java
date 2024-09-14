package com.github.olegushak.FTT.service;

import com.github.olegushak.FTT.repository.TelegramUserRepository;
import com.github.olegushak.FTT.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService{

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository){
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findByChatId(String chat_id) {
        //TODO maybe need to add some handling \ logging for unknown chat_id ?
        // suggest to return not Optional, simple TelegramUser object, and log or throw exception for unknown id
        return telegramUserRepository.findById(chat_id);
    }
}
