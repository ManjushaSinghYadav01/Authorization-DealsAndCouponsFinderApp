package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByEmail(String email);
}
