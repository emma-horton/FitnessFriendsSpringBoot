package com.fitnessfriends.controller;

import com.fitnessfriends.entity.StravaAccount;
import com.fitnessfriends.service.StravaAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/strava")
public class StravaAccountController {

    @Autowired
    private StravaAccountService stravaAccountService;

    @GetMapping("/{userId}")
    public ResponseEntity<StravaAccount> getStravaAccountByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(stravaAccountService.getStravaAccountByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<StravaAccount> linkStravaAccount(@RequestBody StravaAccount stravaAccount) {
        return ResponseEntity.ok(stravaAccountService.linkStravaAccount(stravaAccount));
    }
}