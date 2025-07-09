package com.example.LibraryApis.Controllers;

import com.example.LibraryApis.Models.BookBorrow;
import com.example.LibraryApis.Services.BookBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookborrows")
public class BookBorrowController {
    @Autowired
    private BookBorrowService service;

    @GetMapping
    public List<BookBorrow> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookBorrow> getById(@PathVariable int id) {
        BookBorrow entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public BookBorrow create(@RequestBody BookBorrow entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public BookBorrow update(@PathVariable int id, @RequestBody BookBorrow entity) {
        entity.setBorrowing_ID(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}