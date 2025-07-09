package com.example.LibraryApis.Repository;

import com.example.LibraryApis.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    void deleteByEmail(String email);
}

