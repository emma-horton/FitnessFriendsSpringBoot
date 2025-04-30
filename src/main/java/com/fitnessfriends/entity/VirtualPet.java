package com.fitnessfriends.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "VirtualPets")
public class VirtualPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private String petType;

    @Column(nullable = false)
    private String healthStatus;

    @ManyToOne // Many pets can belong to one user
    @JoinColumn(name = "userId", nullable = false, unique = true) // Foreign key to the "Users" table
    private User user;

    // Getters and Setters
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(int userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setUserId(userId);
    }
}