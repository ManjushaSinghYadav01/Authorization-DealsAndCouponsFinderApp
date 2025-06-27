  package com.auth.controller;


import com.auth.dto.UserProfileDto;
import com.auth.dto.AuthRequest;
import com.auth.entity.UserCredential;
import com.auth.exception.AuthenticationException;
import com.auth.repository.UserCredentialRepository;
import com.auth.service.AuthService;
import com.google.common.base.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }
 
    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        try { 
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = service.generateToken(authRequest.getEmail());
                return ResponseEntity.ok(token);
            } else {
                throw new AuthenticationException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new AuthenticationException("Invalid credentials");
        }
    }



    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam(value = "token", required = false) String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token parameter is missing or empty");
        }
        try {
            service.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable String email) {
        UserProfileDto profile = service.getProfile(email);
        return ResponseEntity.ok(profile);
    }
    @PutMapping("/walletUpdate/{email}")
    public ResponseEntity<String> updateWalletAfterTransaction(
            @PathVariable String email,
            @RequestParam double walletUsed,
            @RequestParam double cashback) {
        service.updateWalletAfterTransaction(email, walletUsed, cashback);
        return ResponseEntity.ok("Wallet updated successfully.");
    }
    @GetMapping("/walletBalance/{email}")
    public ResponseEntity<Double> getUserWalletBalance(@PathVariable String email) {
        Double balance = service.getUserWalletBalance(email);
        return ResponseEntity.ok(balance);
    }

}
