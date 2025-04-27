package com.fitnessfriends.controller;

import com.fitnessfriends.entity.ActivityData;
import com.fitnessfriends.service.ActivityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityDataController {

    @Autowired
    private ActivityDataService activityDataService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityData>> getActivitiesByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(activityDataService.getActivitiesByUserId(userId));
    }

    @GetMapping("/{userId}/type/{activityType}")
    public ResponseEntity<List<ActivityData>> getActivitiesByType(
            @PathVariable int userId,
            @PathVariable String activityType) {
        return ResponseEntity.ok(activityDataService.getActivitiesByType(userId, activityType));
    }

    @PostMapping
    public ResponseEntity<ActivityData> logActivity(@RequestBody ActivityData activity) {
        return ResponseEntity.ok(activityDataService.logActivity(activity));
    }
}