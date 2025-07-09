package com.example.LibraryApis.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "bookborrow")
public class BookBorrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowing_ID;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_ID")
    private Book book;

    private Integer book_Total;
    private Integer isInLibrary;
    private String date_Borrow;
    private String date_Return;

}

