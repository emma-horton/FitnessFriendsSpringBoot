package com.fitnessfriends.controller;

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
    public ResponseEntity<List<VirtualPet>> getPetsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(virtualPetService.getPetsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<VirtualPet> createPet(@RequestBody VirtualPet pet) {
        return ResponseEntity.ok(virtualPetService.createPet(pet));
    }
}