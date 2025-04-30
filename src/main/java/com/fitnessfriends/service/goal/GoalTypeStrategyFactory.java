package com.fitnessfriends.service.goal;

public class GoalTypeStrategyFactory {

    public static GoalTypeStrategy getStrategy(String goalType) {
        switch (goalType.toLowerCase()) {
            case "distance":
                return new DistanceGoal();
            case "duration":
                return new DurationGoal();
            case "frequency":
                return new FrequencyGoal();
            default:
                throw new IllegalArgumentException("Invalid goal type: " + goalType);
        }
    }
}