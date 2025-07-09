package org.example.Service;

import org.example.DTO.BookDTO;
import org.example.Models.Book;
import org.example.Models.Category;
import org.example.Utils.ApiHelper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final String BASE_URL = "http://localhost:8080/api/books";

    private AuthService authService;

    public BookService() {
        this.authService = AuthService.getInstance();
    }

    public List<BookDTO> getAllBooks() {
        String token = authService.getJwtToken();
        return ApiHelper.getList(BASE_URL, BookDTO[].class, token);
    }

    public boolean addBook(BookDTO book) {
        String token = authService.getJwtToken();
        return ApiHelper.post(BASE_URL, book, token);
    }

    public boolean updateBook(BookDTO book) {
        String token = authService.getJwtToken();
        return ApiHelper.put(BASE_URL + "/" + book.getId(), book, token);
    }

    public boolean deleteBook(int id) {
        String token = authService.getJwtToken();
        return ApiHelper.delete(BASE_URL + "/" + id, token);
    }

    public List<BookDTO> searchBook(String keyword) {
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String token = authService.getJwtToken();
        return ApiHelper.getList(BASE_URL + "?keyword=" + encodedKeyword, BookDTO[].class, token);
    }
}
