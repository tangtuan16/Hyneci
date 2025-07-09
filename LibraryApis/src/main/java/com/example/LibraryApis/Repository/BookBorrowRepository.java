package com.example.LibraryApis.Repository;

import com.example.LibraryApis.Models.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowRepository extends JpaRepository<BookBorrow, Integer> {
}