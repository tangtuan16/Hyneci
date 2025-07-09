package com.example.Aglaea.Models;

public class BookBorrow {
    private Integer borrowingId;
    private User user;
    private Book book;
    private Integer bookTotal;
    private Integer isInLibrary;
    private String dateBorrow;
    private String dateReturn;

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBookTotal() {
        return bookTotal;
    }

    public void setBookTotal(Integer bookTotal) {
        this.bookTotal = bookTotal;
    }

    public Integer getIsInLibrary() {
        return isInLibrary;
    }

    public void setIsInLibrary(Integer isInLibrary) {
        this.isInLibrary = isInLibrary;
    }

    public String getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.dateBorrow = dateBorrow;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }
}
