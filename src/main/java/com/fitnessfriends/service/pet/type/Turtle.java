package com.fitnessfriends.service.pet.type;
import com.fitnessfriends.entity.VirtualPet;

public class Turtle extends VirtualPet {
    public String move() {
        return "Swoodh! Swoodh! Swimming in the water.";
    }

    public String eat() {
        return "Munch! Munch! Tasty salad treat.";
    }

    public String play() {
        return "Wiggle Wiggle. Let's play catch!";
    }

    public String sleep() {
        return "Snoozing and snoring in my bowl.";
    }

    public String hibernate() {
        return "Retracting my head and legs into my shell. Very sleepy. I'm going to hibernate in my shell for a while.";
    }
}