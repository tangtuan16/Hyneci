package com.example.LibraryApis.Utils;

import com.example.LibraryApis.DTO.BookDTO;
import com.example.LibraryApis.Models.Book;
import com.example.LibraryApis.Models.Category;

import java.time.LocalDateTime;

public class BookMapper {

    public static Book toBook (BookDTO dto, Category category) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setStock(dto.getStock());
        book.setImageUrl(dto.getImageUrl());
        book.setCreatedAt(LocalDateTime.now());
        book.setCategory(category);
        return book;
    }

    public static BookDTO toBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setStock(book.getStock());
        dto.setImageUrl(book.getImageUrl());
        dto.setCreatedAt(book.getCreatedAt());

        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getId());
            dto.setCategoryName(book.getCategory().getName());
        }
        return dto;
    }
}
