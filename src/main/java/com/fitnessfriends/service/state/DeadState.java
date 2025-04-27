package com.fitnessfriends.service.state;

import com.fitnessfriends.entity.VirtualPet;

public class DeadState implements PetState {
    @Override
    public void displayBehavior(VirtualPet pet) {
        System.out.println("Pet " + pet.getPetName() + " has passed away. :(");
    }
}