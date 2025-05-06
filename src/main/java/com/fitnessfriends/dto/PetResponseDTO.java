package com.fitnessfriends.dto;

import com.fitnessfriends.service.pet.PetHealthStatus;

public class PetResponseDTO {
    private String petName;
    private String petType;
    private PetHealthStatus healthStatus;
    private String behavior; 

    public PetResponseDTO() {}

    public PetResponseDTO(String petName, String petType, PetHealthStatus healthStatus, String behavior) {
        this.petName = petName;
        this.petType = petType;
        this.healthStatus = healthStatus;
        this.behavior = behavior;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public PetHealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(PetHealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    @Override
    public String toString() {
        return "PetResponseDTO{" +
                "petName='" + petName + '\'' +
                ", petType='" + petType + '\'' +
                ", healthStatus=" + healthStatus +
                ", behavior='" + behavior + '\'' +
                '}';
    }
}