package com.fitnessfriends.service;

import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.entity.StravaAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityDataService {

    @Autowired
    private StravaAccountService stravaAccountService;

    // Retrieve all activities for a specific user
    public List<ActivityData> getActivitiesByUserId(int userId) {
        // Fetch the Strava account for the user
        StravaAccount stravaAccount = stravaAccountService.getStravaAccountByUserId(userId);
        if (stravaAccount == null) {
            throw new IllegalArgumentException("No Strava account linked for user with ID: " + userId);
        }
    
        // Ensure the access token is valid (refresh if necessary)
        String validAccessToken = stravaAccountService.ensureValidAccessToken(stravaAccount);
    
        // Use the valid access token to fetch activities from Strava
        return stravaAccountService.fetchActivitiesFromStravaWithToken(validAccessToken);
    }

    // Retrieve activities by type (e.g., "Running", "Cycling")
    public List<ActivityData> getActivitiesByType(int userId, String activityType) {
        // Fetch all activities for the user
        List<ActivityData> activities = getActivitiesByUserId(userId);

        // Filter activities by type
        return activities.stream()
                .filter(activity -> activity.getActivityType().equalsIgnoreCase(activityType))
                .toList();
    }

    // Log a new activity (if needed, this could be extended to interact with Strava's API)
    public ActivityData logActivity(ActivityData activity) {
        throw new UnsupportedOperationException("Logging activities is not supported via Strava API.");
    }
}