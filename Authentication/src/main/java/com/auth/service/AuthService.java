//package com.auth.service;
//
//
//import com.auth.entity.UserCredential;
//import com.auth.repository.UserCredentialRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;

//@Service
//public class AuthService {
//
//    @Autowired
//    private UserCredentialRepository repository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JwtService jwtService;
//
//    public String saveUser(UserCredential credential) {
//        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//        repository.save(credential);
//        return "user added to the system";
//    }
//
//    public String generateToken(String email) {
//        return jwtService.generateToken(email);
//    }
//
//    public void validateToken(String token) {
//        jwtService.validateToken(token);
//    }
//
//
//}
package com.auth.service;


import com.auth.dto.UserProfileDto;

import com.auth.entity.UserCredential;
import com.auth.repository.UserCredentialRepository;
import com.auth.exception.AuthenticationException;
import com.auth.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String email) {
    	  UserCredential user = repository.findByEmail(email)
    	            .orElseThrow(() -> new AuthenticationException("Invalid username or password"));
    	        
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
    	 try {
             jwtService.validateToken(token);
         } catch (Exception e) {
             throw new AuthenticationException("Invalid or expired token");
         }
    }
    
    public UserProfileDto getProfile(String email) throws UserNotFoundException
   	{
   	
           UserCredential user = repository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));

           UserProfileDto dto = new UserProfileDto();
           dto.setId(user.getId());
           dto.setName(user.getName());
           dto.setEmail(user.getEmail());
           dto.setWalletPoints(user.getWalletPoints());

           return dto;
           
       }
    
    //Call by Transaction Services
    public void updateWalletAfterTransaction(String email, double walletUsed, double cashback) {
        UserCredential user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double updatedWallet = user.getWalletPoints() - walletUsed + cashback;
        user.setWalletPoints(updatedWallet);
        repository.save(user);
    }

    public Double getUserWalletBalance(String email) {
        UserCredential user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getWalletPoints();
    }
    //To Show user Profile But Without password
    public UserProfileDto convertToDto(UserCredential user) {
        return new UserProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getWalletPoints()
        );
    }
    
}


