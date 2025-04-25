package com.fitnessfriends.service;

import com.fitnessfriends.entity.StravaAccount;
import com.fitnessfriends.repository.StravaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StravaAccountService {

    @Autowired
    private StravaAccountRepository stravaAccountRepository;

    // Retrieve a Strava account for a specific user
    public StravaAccount getStravaAccountByUserId(int userId) {
        // Corrected method name
        return stravaAccountRepository.findByUserId(userId);
    }

    // Link a new Strava account
    public StravaAccount linkStravaAccount(StravaAccount stravaAccount) {
        return stravaAccountRepository.save(stravaAccount);
    }
}