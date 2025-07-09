package com.example.Aglaea.Services;

import com.example.Aglaea.DTO.BookDTO;
import com.example.Aglaea.Models.Category;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BookService extends BaseApiService {

    @Value("${api.base-url}")
    private String API_BASE_URL;

    public List<BookDTO> getBooks() {
        System.out.println("Books: " + API_BASE_URL);
        String url = API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "books";

        BookDTO[] booksArray = callApi(url, HttpMethod.GET, null, BookDTO[].class, false);
        return booksArray != null ? Arrays.asList(booksArray) : Collections.emptyList();
    }

    public List<Category> getAllCategories() {
        String url = API_BASE_URL + (API_BASE_URL.endsWith("/") ? "" : "/") + "categories";

        Category[] categories = callApi(url, HttpMethod.GET, null, Category[].class, false);
        return categories != null ? Arrays.asList(categories) : Collections.emptyList();
    }


    @Override
    protected String getJwtToken() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return null;

        HttpServletRequest request = attrs.getRequest();
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("JWT_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
