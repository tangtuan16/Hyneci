package com.example.LibraryApis.Services;

import com.example.LibraryApis.Models.BookBorrow;
import com.example.LibraryApis.Repository.BookBorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookBorrowService {
    @Autowired
    private BookBorrowRepository repo;

    public List<BookBorrow> getAll() {
        return repo.findAll();
    }

    public BookBorrow getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public BookBorrow save(BookBorrow entity) {
        return repo.save(entity);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}