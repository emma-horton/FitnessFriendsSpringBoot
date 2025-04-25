package com.fitnessfriends.repository;

import com.fitnessfriends.entity.StravaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StravaAccountRepository extends JpaRepository<StravaAccount, Integer> {
    // Custom query to find a Strava account by user ID
    StravaAccount findByUserId(int userId);
}