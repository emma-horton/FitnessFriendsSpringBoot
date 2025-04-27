package com.fitnessfriends.service;

import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.repository.ActivityDataRepository;
import com.fitnessfriends.repository.HabitGoalRepository;
import com.fitnessfriends.repository.VirtualPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class VirtualPetService {

    @Autowired
    private VirtualPetRepository virtualPetRepository;

    @Autowired
    private HabitGoalRepository habitGoalRepository;

    @Autowired
    private ActivityDataRepository activityDataRepository;

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
        // Fetch the single pet for the user
        VirtualPet pet = virtualPetRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No pet found for user with ID: " + userId));

        // Fetch all goals for the user
        List<HabitGoal> goals = habitGoalRepository.findByUserId(userId);

        // Fetch all activity data for the user
        List<ActivityData> activities = activityDataRepository.findByUserId(userId);

        // Check if any goal is achieved
        boolean goalAchieved = isGoalAchieved(goals, activities);

        // Calculate health based on goal achievement
        String healthStatus = calculateHealth(goalAchieved);

        // Assign health to the pet
        pet.setHealthStatus(healthStatus);

        // Output behavior based on health
        outputPetBehavior(pet);

        return pet;
    }

    private boolean isGoalAchieved(List<HabitGoal> goals, List<ActivityData> activities) {
        // Get the current date
        LocalDate today = LocalDate.now();

        // Calculate the start and end of the current week (Monday to Sunday)
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        // Define a formatter to parse the activityDate (assuming it's stored as a String in "yyyy-MM-dd" format)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Logic to check if any goal is achieved based on activity data
        for (HabitGoal goal : goals) {
            for (ActivityData activity : activities) {
                // Parse the activityDate to a LocalDate
                LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);

                // Check if the activity date is within this week
                if (!activityDate.isBefore(startOfWeek) && !activityDate.isAfter(endOfWeek)) {
                    // Check if the activity type matches the goal's sport
                    if (goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                        // Dynamically fetch the value based on the goal type
                        String goalType = goal.getGoalType().toLowerCase(); // e.g., "distance" or "duration"
                        float activityValue = activity.getValue(goalType);

                        // Check if the activity value meets or exceeds the goal's target value
                        if (activityValue >= goal.getTargetValue()) {
                            return true;
                        }
                    }
                }
            }
        }
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