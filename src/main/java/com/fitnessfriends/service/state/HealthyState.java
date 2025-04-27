package com.fitnessfriends.service.state;

import com.fitnessfriends.entity.VirtualPet;

public class HealthyState implements PetState {
    @Override
    public void displayBehavior(VirtualPet pet) {
        System.out.println("Pet " + pet.getPetName() + " is happy and energetic!");
    }
}