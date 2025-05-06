package com.fitnessfriends.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityData {

    @JsonProperty("id")
    private int dataId;

    @JsonProperty("start_date")
    private String activityDate;

    @JsonProperty("type")
    private String activityType;

    @JsonProperty("distance")
    private float activityDistance;

    @JsonProperty("elapsed_time")
    private float activityDuration;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
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

    public float getActivityDistance() {
        return activityDistance;
    }

    public void setActivityDistance(float activityDistance) {
        this.activityDistance = activityDistance;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
    }
    public float getValue(String metric) {
        switch (metric.toLowerCase()) {
            case "distance":
                return getActivityDistance();
            case "duration":
                return getActivityDuration();
            default:
                throw new IllegalArgumentException("Invalid metric: " + metric);
        }
    }

    @Override
    public String toString() {
        return "ActivityData{" +
                "dataId=" + dataId +
                ", activityDate='" + activityDate + '\'' +
                ", activityType='" + activityType + '\'' +
                ", activityDistance=" + activityDistance +
                ", activityDuration=" + activityDuration +
                '}';
    }
}