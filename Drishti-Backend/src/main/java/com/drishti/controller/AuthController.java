package com.drishti.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        // For MVP, return a dummy token
        return ResponseEntity.ok("Bearer dummy-token-for-" + username);
    }
}