package com.fitnessfriends.repository;

import com.fitnessfriends.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query to find a user by username
    User findByUsername(String username);
}