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
    
        // Fetch activity data from Strava
        List<ActivityData> activities = stravaAccountService.fetchActivitiesFromStrava(stravaAccount.getAccessToken());
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
    
        // Get the current date
        LocalDate today = LocalDate.now();
        logger.debug("Today's date: {}", today);
    
        // Calculate the date 7 days ago
        LocalDate sevenDaysAgo = today.minusDays(7);
        logger.debug("Date 7 days ago: {}", sevenDaysAgo);
    
        // Define a formatter to parse the activityDate (assuming it's stored as a String in "yyyy-MM-dd" format)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        // Logic to check if any goal is achieved based on activity data
        for (HabitGoal goal : goals) {
            logger.debug("Checking goal: {}", goal);
    
            for (ActivityData activity : activities) {
                logger.debug("Checking activity: {}", activity);
    
                // Parse the activityDate to a LocalDate
                LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);
                logger.debug("Parsed activity date: {}", activityDate);
    
                // Check if the activity date is within the past 7 days
                if (!activityDate.isBefore(sevenDaysAgo) && !activityDate.isAfter(today)) {
                    logger.debug("Activity is within the past 7 days.");
    
                    // Check if the activity type matches the goal's sport
                    if (goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                        logger.debug("Activity type matches goal sport: {}", goal.getSport());
    
                        // Dynamically fetch the value based on the goal type
                        String goalType = goal.getGoalType().toLowerCase(); // e.g., "distance" or "duration"
                        float activityValue = activity.getValue(goalType);
                        logger.debug("Activity value for goal type '{}': {}", goalType, activityValue);
    
                        // Check if the activity value meets or exceeds the goal's target value
                        if (activityValue >= goal.getTargetValue()) {
                            logger.debug("Goal achieved! Activity value: {}, Target value: {}", activityValue, goal.getTargetValue());
                            return true;
                        } else {
                            logger.debug("Activity value does not meet the target. Activity value: {}, Target value: {}", activityValue, goal.getTargetValue());
                        }
                    } else {
                        logger.debug("Activity type does not match goal sport. Activity type: {}, Goal sport: {}", activity.getActivityType(), goal.getSport());
                    }
                } else {
                    logger.debug("Activity is not within the past 7 days. Activity date: {}", activityDate);
                }
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