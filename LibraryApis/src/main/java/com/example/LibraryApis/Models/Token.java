package com.example.LibraryApis.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String token;
    private LocalDateTime expireAt;

    public Token(String email, String token, LocalDateTime expireAt) {
        this.email = email;
        this.token = token;
        this.expireAt = expireAt;
    }

    public Token() {

    }
}
