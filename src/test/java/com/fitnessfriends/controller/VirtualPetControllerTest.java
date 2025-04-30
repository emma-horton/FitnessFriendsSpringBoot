package com.fitnessfriends.controller;

import com.fitnessfriends.entity.VirtualPet;
import com.fitnessfriends.service.VirtualPetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VirtualPetControllerTest {

    @InjectMocks
    private VirtualPetController virtualPetController;

    @Mock
    private VirtualPetService virtualPetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPetByUserId_Success() {
        int userId = 1;
        VirtualPet pet = new VirtualPet();
        pet.setPetId(1);
        pet.setPetName("Buddy");

        when(virtualPetService.getPetWithHealthByUserId(userId)).thenReturn(pet);

        ResponseEntity<VirtualPet> response = virtualPetController.getPetByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Buddy", response.getBody().getPetName());
        verify(virtualPetService, times(1)).getPetWithHealthByUserId(userId);
    }

    @Test
    void testGetPetByUserId_NotFound() {
        int userId = 1;

        when(virtualPetService.getPetWithHealthByUserId(userId)).thenReturn(null);

        ResponseEntity<VirtualPet> response = virtualPetController.getPetByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(virtualPetService, times(1)).getPetWithHealthByUserId(userId);
    }

    @Test
    void testCreatePet_Success() {
        VirtualPet pet = new VirtualPet();
        pet.setPetName("Buddy");

        when(virtualPetService.createPet(pet)).thenReturn(pet);

        ResponseEntity<VirtualPet> response = virtualPetController.createPet(pet);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Buddy", response.getBody().getPetName());
        verify(virtualPetService, times(1)).createPet(pet);
    }
}