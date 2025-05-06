package com.fitnessfriends.service.pet.state;

import com.fitnessfriends.entity.VirtualPet;

public class DeadState implements PetState {
    @Override
    public String displayBehavior(VirtualPet pet) {
        return "RIP";
    }

    @Override
    public String move(VirtualPet pet) {
        return pet.getPetName() +" cannot move because they have passed away.";
    }

    @Override
    public String eat(VirtualPet pet) {
        return pet.getPetName() + " cannot eat because they have passed away.";
    }

    @Override
    public String play(VirtualPet pet) {
        return pet.getPetName() + " cannot play because they have passed away.";
    }
}