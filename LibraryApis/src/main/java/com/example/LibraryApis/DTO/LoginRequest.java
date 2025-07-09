package com.example.LibraryApis.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
    private String username;
    private String password;

}
