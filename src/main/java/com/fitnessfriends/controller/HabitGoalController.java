package com.fitnessfriends.controller;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.service.HabitGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class HabitGoalController {

    @Autowired
    private HabitGoalService habitGoalService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<HabitGoal>> getGoalsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(habitGoalService.getGoalsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<HabitGoal> createGoal(@RequestBody HabitGoal goal) {
        return ResponseEntity.ok(habitGoalService.createGoal(goal));
    }
}