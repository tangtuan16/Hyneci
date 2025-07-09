package com.example.LibraryApis.Repository;

import com.example.LibraryApis.Models.FavoriteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Integer> {
}