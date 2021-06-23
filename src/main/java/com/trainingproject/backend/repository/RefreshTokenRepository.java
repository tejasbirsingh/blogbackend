package com.trainingproject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingproject.backend.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
