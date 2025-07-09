package com.example.LibraryApis.Services;

import com.example.LibraryApis.DTO.BookDTO;
import com.example.LibraryApis.Models.Book;
import com.example.LibraryApis.Models.Category;
import com.example.LibraryApis.Repository.BookRepository;
import com.example.LibraryApis.Repository.CategoryRepository;
import com.example.LibraryApis.Utils.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(BookDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Book book = BookMapper.toBook(dto, category);
        return bookRepository.save(book);
    }

    public Book updateBook(int id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setStock(dto.getStock());
        book.setImageUrl(dto.getImageUrl());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);
        return bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Book> getBooksByCategory(Integer categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

}
