package com.fitnessfriends.repository;

import com.fitnessfriends.entity.VirtualPet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualPetRepository extends JpaRepository<VirtualPet, Integer> {
    // Custom query to find all pets belonging to a specific user
    List<VirtualPet> findByUser_UserId(int userId);
}