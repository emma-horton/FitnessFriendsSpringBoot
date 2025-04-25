package com.fitnessfriends.repository;

import com.fitnessfriends.entity.HabitGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitGoalRepository extends JpaRepository<HabitGoal, Integer> {
    // Custom query to find all goals belonging to a specific user
    List<HabitGoal> findByUserId(int userId);
}