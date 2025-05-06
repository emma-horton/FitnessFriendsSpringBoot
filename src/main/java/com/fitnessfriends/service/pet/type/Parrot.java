package com.fitnessfriends.service.pet.type;
import com.fitnessfriends.entity.VirtualPet;

public class Parrot extends VirtualPet {
    public String move() {
        return "Swooshing through the air!";
    }

    public String eat() {
        return "Crunch! Crunch! Tasty seeds.";
    }

    public String play() {
        return "Squawk! Squawk! Let's play walk the plank!";
    }

    public String sleep() {
        return "Snoozing, Snoozing, Drooling.";
    }
    
    public String hibernate() {
        return "Hiding in cage, I want to come out.";
    }
}