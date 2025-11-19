package com.mwm.portal.controller;

import com.mwm.portal.dto.AuthRequest;
import com.mwm.portal.dto.AuthResponse;
import com.mwm.portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    
    // Endpoint temporário para criar o primeiro admin
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody AuthRequest request) {
        return ResponseEntity.ok().build();
    }
}