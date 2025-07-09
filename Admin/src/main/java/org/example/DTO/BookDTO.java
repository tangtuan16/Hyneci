package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Models.Book;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

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
