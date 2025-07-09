package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.DTO.BookDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private List<BookDTO> books;

}
