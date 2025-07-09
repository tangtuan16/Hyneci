package com.example.LibraryApis.DTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String email;
    private String newPassword;
}
