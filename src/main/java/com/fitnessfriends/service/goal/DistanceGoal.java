package com.fitnessfriends.service.goal;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.ActivityData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DistanceGoal implements GoalTypeStrategy {

    @Override
    public boolean isGoalAchieved(HabitGoal goal, List<ActivityData> activities) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        float totalDistance = 0;

        for (ActivityData activity : activities) {
            LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);

            if (!activityDate.isBefore(sevenDaysAgo) && !activityDate.isAfter(today)
                    && goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                totalDistance += activity.getActivityDistance();
            }
        }

        return totalDistance >= goal.getTargetValue();
    }
}