package com.fitnessfriends.service.pet.state;
import com.fitnessfriends.entity.VirtualPet;

public class SickState implements PetState {
    @Override
    public String displayBehavior(VirtualPet pet) {
        return pet.getPetName() + " is feeling unwell. Please take care of them!";
    }

    @Override
    public String move(VirtualPet pet) {
        return pet.hibernate() + " " + pet.sleep();
    }

    @Override
    public String eat(VirtualPet pet) {
        return pet.getPetName() + " is eating reluctantly.";
    }

    @Override
    public String play(VirtualPet pet) {
        return pet.getPetName() + " is too tired to play.";
    }
}