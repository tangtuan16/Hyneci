package com.example.LibraryApis.Services;

import com.example.LibraryApis.Models.Cart;
import com.example.LibraryApis.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository repo;

    public List<Cart> getAll() {
        return repo.findAll();
    }

    public Cart getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Cart save(Cart entity) {
        return repo.save(entity);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}