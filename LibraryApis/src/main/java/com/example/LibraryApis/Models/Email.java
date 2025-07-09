package com.example.LibraryApis.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Email {
    private String email;
    private String subject;
    private String body;

    public Email(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

}
