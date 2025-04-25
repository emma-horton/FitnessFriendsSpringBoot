package com.fitnessfriends.repository;

import com.fitnessfriends.entity.ActivityData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityDataRepository extends JpaRepository<ActivityData, Integer> {
    // Custom query to find all activities belonging to a specific user
    List<ActivityData> findByUserId(int userId);

    // Custom query to find activities by type (e.g., "Running", "Cycling")
    List<ActivityData> findByActivityType(String activityType);
}