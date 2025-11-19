package com.mwm.portal.service;

import com.mwm.portal.dto.AuthRequest;
import com.mwm.portal.dto.AuthResponse;
import com.mwm.portal.model.User;
import com.mwm.portal.repository.UserRepository;
import com.mwm.portal.security.JwtService;
import com.mwm.portal.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Método para criar usuário inicial
    public AuthResponse register(AuthRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.ADMINISTRADOR);
        repository.save(user);
        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        return AuthResponse.builder().token(jwtToken).build();
    }
}