package com.fitnessfriends.repository;
import java.util.Optional;

import com.fitnessfriends.entity.VirtualPet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VirtualPetRepository extends JpaRepository<VirtualPet, Integer> {
    Optional<VirtualPet> findByUser_UserId(int userId);
}