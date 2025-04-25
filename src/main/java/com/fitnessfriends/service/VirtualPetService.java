package com.fitnessfriends.service;

import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.repository.VirtualPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualPetService {

    @Autowired
    private VirtualPetRepository virtualPetRepository;

    // Retrieve all pets for a specific user
    public List<VirtualPet> getPetsByUserId(int userId) {
        // Corrected method name
        return virtualPetRepository.findByUser_UserId(userId);
    }

    // Create a new virtual pet
    public VirtualPet createPet(VirtualPet pet) {
        return virtualPetRepository.save(pet);
    }
}