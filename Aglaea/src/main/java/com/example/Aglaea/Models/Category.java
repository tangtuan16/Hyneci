package com.example.Aglaea.Models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Category {
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private List<Book> books;

    public Category(Long id, String name, String description, LocalDateTime createdAt, List<Book> books) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.books = books;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
