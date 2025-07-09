package com.example.Aglaea.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {

    private int id;

    private String title;

    private String author;

    private String description;

    private Integer stock;

    private String imageUrl;

    private LocalDateTime createdAt;

    private Category category;
}
