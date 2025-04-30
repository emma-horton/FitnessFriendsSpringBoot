package com.fitnessfriends.service.goal;

import com.fitnessfriends.entity.HabitGoal;
import com.fitnessfriends.entity.ActivityData;

import java.util.List;

public interface GoalTypeStrategy {
    boolean isGoalAchieved(HabitGoal goal, List<ActivityData> activities);
}