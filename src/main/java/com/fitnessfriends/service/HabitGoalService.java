package com.fitnessfriends.service;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.repository.HabitGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitGoalService {

    @Autowired
    private HabitGoalRepository habitGoalRepository;

    // Retrieve all goals for a specific user
    public List<HabitGoal> getGoalsByUserId(int userId) {
        return habitGoalRepository.findByUserId(userId);
    }

    // Create a new fitness goal
    public HabitGoal createGoal(HabitGoal goal) {
        return habitGoalRepository.save(goal);
    }
}