package com.fitnessfriends.service.pet;
import com.fitnessfriends.service.pet.state.PetState;
import com.fitnessfriends.service.pet.state.HealthyState;
import com.fitnessfriends.service.pet.state.SickState;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.service.pet.state.DeadState;

public class PetBehaviour {
    private VirtualPet pet;
    private PetState state;

    public PetBehaviour(VirtualPet pet) {
        this.pet = pet;
        updateState();
    }

    public VirtualPet getPet() {
        return pet;
    }

    public void updateState() {
        switch (pet.getHealthStatus()) { 
            case HEALTHY:
                state = new HealthyState();
                break;
            case SICK:
                state = new SickState();
                break;
            case DEAD:
                state = new DeadState();
                break;
            default:
                throw new IllegalArgumentException("Unknown health status: " + pet.getHealthStatus());
        }
    }

    public void tryToPlay() {
        state.displayBehavior(pet);
    }
    public PetState getState() { 
        return state;
    }
}