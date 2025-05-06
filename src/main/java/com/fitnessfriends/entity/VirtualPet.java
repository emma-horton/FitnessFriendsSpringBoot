package com.fitnessfriends.entity;

import jakarta.persistence.*;
import com.fitnessfriends.service.pet.PetBehaviour;
import com.fitnessfriends.service.pet.PetHealthStatus;

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
    @Enumerated(EnumType.STRING)
    private PetHealthStatus healthStatus;

    @Transient // Not persisted in the database
    private PetBehaviour behaviour;

    // Getters and Setters
    public PetBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(PetBehaviour behaviour) {
        this.behaviour = behaviour;
    }

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

    public PetHealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(PetHealthStatus healthStatus) {
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

    // Default implementations for behaviors
    public String move() {
        return "Default move behavior for VirtualPet.";
    }

    public String eat() {
        return "Default eat behavior for VirtualPet.";
    }

    public String play() {
        return "Default play behavior for VirtualPet.";
    }

    public String hibernate() {
        return "Default hibernate behavior for VirtualPet.";
    }
    public String sleep() {
        return "Default sleep behavior for VirtualPet.";
    }
}