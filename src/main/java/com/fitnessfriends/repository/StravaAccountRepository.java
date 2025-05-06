package com.fitnessfriends.repository;

import com.fitnessfriends.entity.StravaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StravaAccountRepository extends JpaRepository<StravaAccount, Integer> {
    StravaAccount findByUserId(int userId);
}