package com.example.Aglaea.Controllers;

import com.example.Aglaea.DTO.BookDTO;
import com.example.Aglaea.Models.Category;
import com.example.Aglaea.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        List<BookDTO> bookPage = bookService.getBooks();
        model.addAttribute("books", bookPage);
        List<Category> categories = bookService.getAllCategories();
        model.addAttribute("categories", categories);
        Long categoryId = 0L;
        model.addAttribute("categoryId", categoryId);
        return "index";
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        List<BookDTO> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "fragments/books/books-list";
    }

}
