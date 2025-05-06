package com.fitnessfriends.service.pet.state;

import com.fitnessfriends.entity.VirtualPet;

public class HealthyState implements PetState {
    @Override
    public String displayBehavior(VirtualPet pet) {
        return pet.getPetName() + " is gleaming with health and energy!";
    }

    @Override
    public String move(VirtualPet pet) {
        return pet.move();
    }

    @Override
    public String eat(VirtualPet pet) {
        return pet.eat(); 
    }

    @Override
    public String play(VirtualPet pet) {
        return pet.play(); 
    }
}