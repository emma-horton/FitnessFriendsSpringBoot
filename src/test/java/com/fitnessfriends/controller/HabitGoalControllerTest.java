package com.fitnessfriends.controller;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.service.HabitGoalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HabitGoalControllerTest {

    @InjectMocks
    private HabitGoalController habitGoalController;

    @Mock
    private HabitGoalService habitGoalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGoalsByUserId_Success() {
        int userId = 1;
        List<HabitGoal> goals = new ArrayList<>();
        HabitGoal goal1 = new HabitGoal();
        goal1.setGoalId(1);
        goal1.setUserId(userId);
        goal1.setGoalType("distance");
        goal1.setSport("run");
        goal1.setTargetValue(10.0f);
        goals.add(goal1);

        when(habitGoalService.getGoalsByUserId(userId)).thenReturn(goals);

        ResponseEntity<List<HabitGoal>> response = habitGoalController.getGoalsByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("distance", response.getBody().get(0).getGoalType());
        assertEquals("run", response.getBody().get(0).getSport());
        assertEquals(10.0f, response.getBody().get(0).getTargetValue());
        verify(habitGoalService, times(1)).getGoalsByUserId(userId);
    }

    @Test
    void testGetGoalsByUserId_EmptyList() {
        int userId = 1;

        when(habitGoalService.getGoalsByUserId(userId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<HabitGoal>> response = habitGoalController.getGoalsByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(habitGoalService, times(1)).getGoalsByUserId(userId);
    }

    @Test
    void testCreateGoal_Success() {
        HabitGoal goal = new HabitGoal();
        goal.setGoalId(1);
        goal.setUserId(1);
        goal.setGoalType("duration");
        goal.setSport("swim");
        goal.setTargetValue(30.0f);

        when(habitGoalService.createGoal(goal)).thenReturn(goal);

        ResponseEntity<HabitGoal> response = habitGoalController.createGoal(goal);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("duration", response.getBody().getGoalType());
        assertEquals("swim", response.getBody().getSport());
        assertEquals(30.0f, response.getBody().getTargetValue());
        verify(habitGoalService, times(1)).createGoal(goal);
    }

    @Test
    void testCreateGoal_InvalidSport() {
        HabitGoal goal = new HabitGoal();
        goal.setGoalId(1);
        goal.setUserId(1);
        goal.setGoalType("frequency");
        goal.setSport("invalidSport"); // Invalid sport
        goal.setTargetValue(5.0f);

        when(habitGoalService.createGoal(goal)).thenThrow(new IllegalArgumentException("Invalid sport type"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            habitGoalController.createGoal(goal);
        });

        assertEquals("Invalid sport type", exception.getMessage());
        verify(habitGoalService, times(1)).createGoal(goal);
    }
}