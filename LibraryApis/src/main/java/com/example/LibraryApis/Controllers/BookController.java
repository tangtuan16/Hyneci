package com.example.LibraryApis.Controllers;

import com.example.LibraryApis.DTO.BookDTO;
import com.example.LibraryApis.Models.Book;
import com.example.LibraryApis.Services.BookService;
import com.example.LibraryApis.Utils.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        BookDTO dto = BookMapper.toBookDTO(book);
        return dto;
    }

    @GetMapping
    @ResponseBody
    public List<BookDTO> getBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer category
    ) {
        List<Book> books;

        if (category != null) {
            books = bookService.getBooksByCategory(category);
        } else if (keyword != null && !keyword.isBlank()) {
            books = bookService.searchBooks(keyword);
        } else {
            books = bookService.getAllBooks();
        }

        return books.stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.createBook(bookDTO);
        return BookMapper.toBookDTO(savedBook);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bookService.deleteBook(id);
    }

}
