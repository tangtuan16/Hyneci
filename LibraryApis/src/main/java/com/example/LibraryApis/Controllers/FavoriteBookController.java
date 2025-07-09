package com.example.LibraryApis.Controllers;

import com.example.LibraryApis.Models.FavoriteBook;
import com.example.LibraryApis.Services.FavoriteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteBookController {
    @Autowired
    private FavoriteBookService service;

    @GetMapping
    public List<FavoriteBook> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteBook> getById(@PathVariable int id) {
        FavoriteBook entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FavoriteBook create(@RequestBody FavoriteBook entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public FavoriteBook update(@PathVariable int id, @RequestBody FavoriteBook entity) {
        entity.setFavorite_id(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
