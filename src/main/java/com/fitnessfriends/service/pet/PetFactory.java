package com.fitnessfriends.service.pet;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.service.pet.type.Parrot;
import com.fitnessfriends.service.pet.type.Turtle;

public class PetFactory {
    public static PetBehaviour createPet(String type, int petId, PetHealthStatus healthStatus, String name) {
        VirtualPet pet;

        switch (type.toLowerCase()) {
            case "parrot":
                pet = new Parrot();
                break;
            case "turtle":
                pet = new Turtle();
                break;
            default:
                throw new IllegalArgumentException("Unknown pet type: " + type);
        }

        pet.setPetId(petId);
        pet.setHealthStatus(healthStatus);
        pet.setPetName(name);

        return new PetBehaviour(pet);
    }
}