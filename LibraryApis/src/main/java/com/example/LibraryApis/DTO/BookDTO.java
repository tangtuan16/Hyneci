package com.example.LibraryApis.DTO;

import com.example.LibraryApis.Models.Book;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private String description;
    private int stock;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private LocalDateTime createdAt;

}
