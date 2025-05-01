package com.fitnessfriends.service.goal;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.ActivityData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DurationGoal implements GoalTypeStrategy {

    @Override
    public boolean isGoalAchieved(HabitGoal goal, List<ActivityData> activities) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        float totalDuration = 0;

        for (ActivityData activity : activities) {
            LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);

            if (!activityDate.isBefore(sevenDaysAgo) && !activityDate.isAfter(today)
                    && goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                totalDuration += activity.getActivityDuration();
            }
        }

        return totalDuration >= goal.getTargetValue();
    }
    @Override
    public boolean wasLastWeeksGoalAchieved(HabitGoal goal, List<ActivityData> activities) {
        LocalDate today = LocalDate.now();
        LocalDate fourteenDaysAgo = today.minusDays(14);
        LocalDate sevenDaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        float totalDuration = 0;

        for (ActivityData activity : activities) {
            LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);

            if (!activityDate.isBefore(fourteenDaysAgo) && activityDate.isBefore(sevenDaysAgo)
                    && goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                totalDuration += activity.getActivityDuration();
            }
        }

        return totalDuration >= goal.getTargetValue();
    }
}