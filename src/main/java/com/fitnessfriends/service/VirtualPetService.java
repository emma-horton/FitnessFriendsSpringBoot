package com.fitnessfriends.service;

import com.fitnessfriends.controller.UserController;
import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.StravaAccount;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.repository.HabitGoalRepository;
import com.fitnessfriends.repository.VirtualPetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fitnessfriends.repository.StravaAccountRepository;
import com.fitnessfriends.service.StravaAccountService;
import com.fitnessfriends.service.goal.GoalTypeStrategy;
import com.fitnessfriends.service.goal.GoalTypeStrategyFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Service
public class VirtualPetService {

    private static final Logger logger = LoggerFactory.getLogger(VirtualPetService.class);

    @Autowired
    private VirtualPetRepository virtualPetRepository;

    @Autowired
    private HabitGoalRepository habitGoalRepository;

    @Autowired
    private StravaAccountRepository stravaAccountRepository;

    @Autowired
    private StravaAccountService stravaAccountService;

    // Retrieve all pets for a specific user
    public VirtualPet getPetByUserId(int userId) {
        return virtualPetRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No pet found for user with ID: " + userId));
    }

    // Create a new virtual pet
    public VirtualPet createPet(VirtualPet pet) {
        // if (virtualPetRepository.existsByUserId(pet.getUser().getUserId())) {
        //     throw new IllegalArgumentException("Each user can only have one pet.");
        // }
        return virtualPetRepository.save(pet);
    }

    public VirtualPet getPetWithHealthByUserId(int userId) {
        logger.debug("Fetching pet for user ID: {}", userId);
    
        // Fetch the single pet for the user
        VirtualPet pet = virtualPetRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No pet found for user with ID: " + userId));
        logger.debug("Fetched pet: {}", pet);
    
        // Fetch the user's Strava account to get the access token
        StravaAccount stravaAccount = stravaAccountRepository.findByUserId(userId);
        if (stravaAccount == null) {
            throw new IllegalArgumentException("No Strava account linked for user with ID: " + userId);
        }
        logger.debug("Fetched Strava account: {}", stravaAccount);
        logger.debug("Access token: {}", stravaAccount.getAccessToken());
    
        // Ensure the access token is valid (refresh if necessary)
        String validAccessToken = stravaAccountService.ensureValidAccessToken(stravaAccount);
        logger.debug("Validated access token: {}", validAccessToken);
    
        // Fetch activity data from Strava using the valid access token
        List<ActivityData> activities = stravaAccountService.fetchActivitiesFromStravaWithToken(validAccessToken);
        logger.debug("Fetched activities: {}", activities);
    
        // Fetch the user's goals
        List<HabitGoal> goals = habitGoalRepository.findByUserId(userId);
        logger.debug("Fetched goals: {}", goals);
    
        // Check if any goal is achieved
        boolean goalAchieved = isGoalAchieved(goals, activities);
        logger.debug("Goal achieved: {}", goalAchieved);
    
        // Calculate health based on goal achievement
        String healthStatus = calculateHealth(goalAchieved);
        logger.debug("Calculated health status: {}", healthStatus);
    
        // Assign health to the pet
        pet.setHealthStatus(healthStatus);
    
        return pet;
    }

    private boolean isGoalAchieved(List<HabitGoal> goals, List<ActivityData> activities) {
        logger.debug("Checking if any goal is achieved...");
        logger.debug("Goals: {}", goals);
        logger.debug("Activities: {}", activities);

        for (HabitGoal goal : goals) {
            logger.debug("Checking goal: {}", goal);

            // Get the appropriate strategy for the goal type
            GoalTypeStrategy strategy = GoalTypeStrategyFactory.getStrategy(goal.getGoalType());

            // Use the strategy to check if the goal is achieved
            if (strategy.isGoalAchieved(goal, activities)) {
                logger.debug("Goal achieved: {}", goal);
                return true;
            }
        }

        logger.debug("No goals were achieved.");
        return false;
    }

    private String calculateHealth(boolean goalAchieved) {
        // Logic to calculate health based on goal achievement
        return goalAchieved ? "Healthy" : "Sick";
    }

    private void outputPetBehavior(VirtualPet pet) {
        // Logic to output pet behavior based on health
        System.out.println("Pet: " + pet.getPetName() + ", Health: " + pet.getHealthStatus());
    }
}