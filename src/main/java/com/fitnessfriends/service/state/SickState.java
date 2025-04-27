package com.fitnessfriends.service.state;
import com.fitnessfriends.entity.VirtualPet;

public class SickState implements PetState {
    @Override
    public void displayBehavior(VirtualPet pet) {
        System.out.println("Pet " + pet.getPetName() + " is feeling unwell. Please take care of it!");
    }
}