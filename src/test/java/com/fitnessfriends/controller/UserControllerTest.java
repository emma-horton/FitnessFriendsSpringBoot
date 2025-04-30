package com.fitnessfriends.controller;

import com.fitnessfriends.entity.User;
import com.fitnessfriends.dto.StravaTokenResponse;
import com.fitnessfriends.service.StravaAccountService;
import com.fitnessfriends.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;





class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private StravaAccountService stravaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testRegisterUser_Success() {
    //     User user = new User();
    //     user.setUsername("testuser");
    //     user.setPassword("password");

    //     when(userService.registerUser("testuser", "password")).thenReturn(user);

    //     ResponseEntity<String> response = userController.registerUser(user);

    //     assertEquals(200, response.getStatusCodeValue());
    //     assertTrue(response.getBody().contains("User registered successfully!"));
    //     verify(userService, times(1)).registerUser("testuser", "password");
    // }

    // @Test
    // void testRegisterUser_Failure() {
    //     User user = new User();
    //     user.setUsername("testuser");
    //     user.setPassword("password");

    //     when(userService.registerUser("testuser", "password")).thenThrow(new IllegalArgumentException("Username already exists"));

    //     ResponseEntity<String> response = userController.registerUser(user);

    //     assertEquals(400, response.getStatusCodeValue());
    //     assertTrue(response.getBody().contains("Registration failed: Username already exists"));
    //     verify(userService, times(1)).registerUser("testuser", "password");
    // }

    // @Test
    // void testHandleStravaCallback_Success() {
    //     String authorizationCode = "authCode";
    //     String state = "testuser";
    //     User user = new User();
    //     user.setUsername("testuser");
    //     user.setUserId(1L);

    //     StravaTokenResponse tokenResponse = new StravaTokenResponse();

    //     when(userService.findByUsername("testuser")).thenReturn(user);
    //     when(stravaService.exchangeAuthorizationCode(authorizationCode)).thenReturn(tokenResponse);

    //     ResponseEntity<String> response = userController.handleStravaCallback(authorizationCode, state);

    //     assertEquals(200, response.getStatusCodeValue());
    //     assertTrue(response.getBody().contains("Strava account authorized successfully!"));
    //     verify(userService, times(1)).findByUsername("testuser");
    //     verify(stravaService, times(1)).exchangeAuthorizationCode(authorizationCode);
    //     verify(stravaService, times(1)).saveStravaTokens(1L, tokenResponse);
    // }

    // @Test
    // void testHandleStravaCallback_UserNotFound() {
    //     String authorizationCode = "authCode";
    //     String state = "unknownuser";

    //     when(userService.findByUsername("unknownuser")).thenReturn(null);

    //     ResponseEntity<String> response = userController.handleStravaCallback(authorizationCode, state);

    //     assertEquals(400, response.getStatusCodeValue());
    //     assertTrue(response.getBody().contains("User not found for username: unknownuser"));
    //     verify(userService, times(1)).findByUsername("unknownuser");
    //     verifyNoInteractions(stravaService);
    // }

    @Test
    void testLoginUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.authenticateUser("testuser", "password")).thenReturn(user);

        ResponseEntity<User> response = userController.loginUser(user);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
        verify(userService, times(1)).authenticateUser("testuser", "password");
    }

    @Test
    void testLoginUser_Failure() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");

        when(userService.authenticateUser("testuser", "wrongpassword")).thenThrow(new IllegalArgumentException("Invalid credentials"));

        ResponseEntity<User> response = userController.loginUser(user);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).authenticateUser("testuser", "wrongpassword");
    }
}