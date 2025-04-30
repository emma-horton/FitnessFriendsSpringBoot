package com.fitnessfriends.service.goal;
import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.ActivityData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrequencyGoal implements GoalTypeStrategy {

    @Override
    public boolean isGoalAchieved(HabitGoal goal, List<ActivityData> activities) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Set<LocalDate> uniqueDays = new HashSet<>();

        for (ActivityData activity : activities) {
            LocalDate activityDate = LocalDate.parse(activity.getActivityDate(), formatter);

            if (!activityDate.isBefore(sevenDaysAgo) && !activityDate.isAfter(today)
                    && goal.getSport().equalsIgnoreCase(activity.getActivityType())) {
                uniqueDays.add(activityDate);
            }
        }

        return uniqueDays.size() >= goal.getTargetValue();
    }
}