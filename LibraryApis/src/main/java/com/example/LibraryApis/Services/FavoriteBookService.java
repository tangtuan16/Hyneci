package com.example.LibraryApis.Services;

import com.example.LibraryApis.Models.FavoriteBook;
import com.example.LibraryApis.Repository.FavoriteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteBookService {
    @Autowired
    private FavoriteBookRepository repo;

    public List<FavoriteBook> getAll() {
        return repo.findAll();
    }

    public FavoriteBook getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public FavoriteBook save(FavoriteBook entity) {
        return repo.save(entity);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}