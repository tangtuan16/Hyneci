package com.example.LibraryApis.Controllers;

import com.example.LibraryApis.Models.Cart;

import com.example.LibraryApis.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping
    public List<Cart> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getById(@PathVariable int id) {
        Cart entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Cart create(@RequestBody Cart entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public Cart update(@PathVariable int id, @RequestBody Cart entity) {
        entity.setCartid(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}