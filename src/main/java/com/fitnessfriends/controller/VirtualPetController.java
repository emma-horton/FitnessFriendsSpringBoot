package com.fitnessfriends.controller;

import com.fitnessfriends.dto.PetResponseDTO;
import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.service.VirtualPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class VirtualPetController {

    @Autowired
    private VirtualPetService virtualPetService;

    @GetMapping("/{userId}")
    public ResponseEntity<PetResponseDTO> getPetByUserId(@PathVariable int userId) {
        VirtualPet pet = virtualPetService.getPetWithHealthByUserId(userId);

        // Determine the pet's behavior based on its state
        String behavior = "";
        if (pet.getBehaviour() != null) {
            if (pet.getBehaviour().getState() != null) {
                behavior = pet.getBehaviour().getState().displayBehavior(pet);
            } else {
                System.out.println("State is null for pet: " + pet.getPetName());
            }
        } else {
            System.out.println("PetBehaviour is null for pet: " + pet.getPetName());
        }

        // Map VirtualPet to PetResponseDTO
        PetResponseDTO response = new PetResponseDTO(
                pet.getPetName(),
                pet.getPetType(),
                pet.getHealthStatus(),
                behavior // Include behavior in the response
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/move")
    public ResponseEntity<PetResponseDTO> movePet(@PathVariable int userId) {
        VirtualPet pet = virtualPetService.getPetWithHealthByUserId(userId);

        // Determine the pet's behavior based on its state
        String behavior = "";
        if (pet.getBehaviour() != null) {
            if (pet.getBehaviour().getState() != null) {
                behavior = pet.getBehaviour().getState().move(pet);
            } else {
                System.out.println("State is null for pet: " + pet.getPetName());
            }
        } else {
            System.out.println("PetBehaviour is null for pet: " + pet.getPetName());
        }

        // Map VirtualPet to PetResponseDTO
        PetResponseDTO response = new PetResponseDTO(
                pet.getPetName(),
                pet.getPetType(),
                pet.getHealthStatus(),
                behavior // Include behavior in the response
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/feed")
    public ResponseEntity<PetResponseDTO> feedPet(@PathVariable int userId) {
        VirtualPet pet = virtualPetService.getPetWithHealthByUserId(userId);

        // Determine the pet's behavior based on its state
        String behavior = "";
        if (pet.getBehaviour() != null) {
            if (pet.getBehaviour().getState() != null) {
                behavior = pet.getBehaviour().getState().eat(pet);
            } else {
                System.out.println("State is null for pet: " + pet.getPetName());
            }
        } else {
            System.out.println("PetBehaviour is null for pet: " + pet.getPetName());
        }

        // Map VirtualPet to PetResponseDTO
        PetResponseDTO response = new PetResponseDTO(
                pet.getPetName(),
                pet.getPetType(),
                pet.getHealthStatus(),
                behavior // Include behavior in the response
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/play")
    public ResponseEntity<PetResponseDTO> playWithPet(@PathVariable int userId) {
        VirtualPet pet = virtualPetService.getPetWithHealthByUserId(userId);

        // Determine the pet's behavior based on its state
        String behavior = "";
        if (pet.getBehaviour() != null) {
            if (pet.getBehaviour().getState() != null) {
                behavior = pet.getBehaviour().getState().play(pet);
            } else {
                System.out.println("State is null for pet: " + pet.getPetName());
            }
        } else {
            System.out.println("PetBehaviour is null for pet: " + pet.getPetName());
        }

        // Map VirtualPet to PetResponseDTO
        PetResponseDTO response = new PetResponseDTO(
                pet.getPetName(),
                pet.getPetType(),
                pet.getHealthStatus(),
                behavior // Include behavior in the response
        );

        return ResponseEntity.ok(response);
    }
}