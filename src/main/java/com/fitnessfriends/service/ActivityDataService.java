package com.fitnessfriends.service;

import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.repository.ActivityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityDataService {

    @Autowired
    private ActivityDataRepository activityDataRepository;

    // Retrieve all activities for a specific user
    public List<ActivityData> getActivitiesByUserId(int userId) {
        return activityDataRepository.findByUserId(userId);
    }

    // Retrieve activities by type (e.g., "Running", "Cycling")
    public List<ActivityData> getActivitiesByType(String activityType) {
        return activityDataRepository.findByActivityType(activityType);
    }

    // Log a new activity
    public ActivityData logActivity(ActivityData activity) {
        return activityDataRepository.save(activity);
    }
}