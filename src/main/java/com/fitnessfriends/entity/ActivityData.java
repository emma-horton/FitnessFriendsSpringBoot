package com.fitnessfriends.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ActivityData")
public class ActivityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dataId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String activityDate;

    @Column(nullable = false)
    private String activityType;

    @Column(nullable = false)
    private float activityDuration;

    @Column(nullable = false)
    private float activityDistance;

    // Getters and Setters
    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
    }

    public float getActivityDistance() {
        return activityDistance;
    }

    public void setActivityDistance(float activityDistance) {
        this.activityDistance = activityDistance;
    }
}