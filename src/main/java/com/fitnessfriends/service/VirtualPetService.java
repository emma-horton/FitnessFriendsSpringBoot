package com.fitnessfriends.service;

import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.repository.HabitGoalRepository;
import com.fitnessfriends.repository.VirtualPetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitnessfriends.repository.StravaAccountRepository;
import com.fitnessfriends.service.goal.GoalTypeStrategyFactory;
import com.fitnessfriends.service.pet.PetBehaviour;
import java.util.List;

import com.fitnessfriends.service.pet.PetHealthStatus;
import com.fitnessfriends.service.pet.type.Parrot;
import com.fitnessfriends.service.pet.type.Turtle;

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

    public VirtualPet getPetWithHealthByUserId(int userId) {
        // Fetch the pet from the repository
        VirtualPet pet = virtualPetRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No pet found for user with ID: " + userId));

        // Use the factory method to create the correct subclass
        pet = createPetInstance(pet);

        // Fetch activity data and goals
        List<ActivityData> activities = stravaAccountService.fetchActivitiesFromStravaWithToken(
                stravaAccountService.ensureValidAccessToken(
                        stravaAccountService.getStravaAccountByUserId(userId)
                )
        );
        List<HabitGoal> goals = habitGoalRepository.findByUserId(userId);

        // Update pet health
        updatePetHealth(pet, goals, activities);

        // Set the pet's behavior
        PetBehaviour petBehaviour = new PetBehaviour(pet);
        pet.setBehaviour(petBehaviour);

        return pet;
    }

    private VirtualPet createPetInstance(VirtualPet pet) {
        switch (pet.getPetType().toUpperCase()) {
            case "TURTLE":
                Turtle turtle = new Turtle();
                turtle.setPetId(pet.getPetId());
                turtle.setPetName(pet.getPetName());
                turtle.setPetType(pet.getPetType());
                turtle.setHealthStatus(pet.getHealthStatus());
                return turtle;
            case "PARROT":
                Parrot parrot = new Parrot();
                parrot.setPetId(pet.getPetId());
                parrot.setPetName(pet.getPetName());
                parrot.setPetType(pet.getPetType());
                parrot.setHealthStatus(pet.getHealthStatus());
                return parrot;
            default:
                return pet; // Default to VirtualPet if no specific type is found
        }
    }

    private void updatePetHealth(VirtualPet pet, List<HabitGoal> goals, List<ActivityData> activities) {
        boolean allGoalsAchievedThisWeek = areAllGoalsAchievedThisWeek(goals, activities);
        boolean someGoalsAchievedThisWeek = areSomeGoalsAchievedThisWeek(goals, activities);
        boolean allGoalsMissedForTwoWeeks = areAllGoalsMissedForTwoWeeks(goals, activities);

        if (allGoalsAchievedThisWeek) {
            pet.setHealthStatus(PetHealthStatus.HEALTHY);
        } else if (someGoalsAchievedThisWeek) {
            pet.setHealthStatus(PetHealthStatus.SICK);
        } else if (allGoalsMissedForTwoWeeks) {
            pet.setHealthStatus(PetHealthStatus.DEAD);
        }

        // Update the pet's state
        PetBehaviour petBehaviour = new PetBehaviour(pet);
        petBehaviour.updateState();
    }

    private boolean areAllGoalsAchievedThisWeek(List<HabitGoal> goals, List<ActivityData> activities) {
        return goals.stream()
                .allMatch(goal -> GoalTypeStrategyFactory.getStrategy(goal.getGoalType()).isGoalAchieved(goal, activities));
    }

    private boolean areSomeGoalsAchievedThisWeek(List<HabitGoal> goals, List<ActivityData> activities) {
        return goals.stream()
                .anyMatch(goal -> GoalTypeStrategyFactory.getStrategy(goal.getGoalType()).isGoalAchieved(goal, activities));
    }

    private boolean areAllGoalsMissedForTwoWeeks(List<HabitGoal> goals, List<ActivityData> activities) {
        return goals.stream()
                .noneMatch(goal -> GoalTypeStrategyFactory.getStrategy(goal.getGoalType()).isGoalAchieved(goal, activities)
                        || GoalTypeStrategyFactory.getStrategy(goal.getGoalType()).wasLastWeeksGoalAchieved(goal, activities));
    }
}