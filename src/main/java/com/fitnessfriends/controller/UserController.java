package com.fitnessfriends.controller;
import com.fitnessfriends.dto.StravaTokenResponse;

import com.fitnessfriends.entity.User;
import com.fitnessfriends.service.StravaAccountService;
import com.fitnessfriends.service.UserService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StravaAccountService stravaService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            // Register the user
            User registeredUser = userService.registerUser(user.getUsername(), user.getPassword());

            // Encode the username as the state parameter
            String state = registeredUser.getUsername();
            String dummyData = "redirect";
            logger.debug("Username: {}", state);

            // Redirect the user to the Strava authorization page
            String stravaAuthUrl = "https://www.strava.com/oauth/authorize" +
                    "?client_id=153559" +
                    "&redirect_uri=http://localhost:8080/api/users/strava/callback" +
                    "&response_type=code" +
                    "&scope=read_all,activity:read_all" +
                    "&state=" + state +
                    "&dummyData=" + dummyData;

            return ResponseEntity.ok("User registered successfully! Please authorize your Strava account: " + stravaAuthUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
  
    @GetMapping("/strava/callback")
    public ResponseEntity<String> handleStravaCallback(
            @RequestParam("code") String authorizationCode,
            @RequestParam("state") String state) {
        try {
            logger.debug("Recieved username: {}", state);
            // Decode the state parameter to get the username
            String username = URLDecoder.decode(state, StandardCharsets.UTF_8);
            logger.debug("Decoded username: {}", state);

            // Find the user by username
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found for username: " + username);
            }

            // Exchange the authorization code for tokens
            StravaTokenResponse tokenResponse = stravaService.exchangeAuthorizationCode(authorizationCode);

            // Save the tokens to the database
            stravaService.saveStravaTokens(user.getUserId(), tokenResponse);

            return ResponseEntity.ok("Strava account authorized successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to authorize Strava account: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        try {
            User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}