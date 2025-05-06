package com.fitnessfriends.service.pet.state;

import com.fitnessfriends.entity.VirtualPet;
public interface PetState {
    String displayBehavior(VirtualPet pet);
    String move(VirtualPet pet); 
    String eat(VirtualPet pet);  
    String play(VirtualPet pet);
}
