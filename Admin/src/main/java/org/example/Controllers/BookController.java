package org.example.Controllers;


import org.example.DTO.BookDTO;
import org.example.Service.BookService;
import org.example.Views.BookFrame;

import javax.swing.*;
import java.util.List;

public class BookController {
    private BookService bookService;
    private BookFrame view;

    public BookController(BookFrame view) {
        this.view = view;
        this.bookService = new BookService();
        initListeners();
        loadBooks();
    }

    public void loadBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        view.setBookTable(books);
        view.refreshInput();
    }


    private void initListeners() {
        view.setAddBookListener(book -> {
            boolean success = bookService.addBook(book);
            System.out.println("Book added: " + book.toString());
            if (success) {
                JOptionPane.showMessageDialog(view, "Thêm sách thành công!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi thêm sách.");
            }
        });

        view.setUpdateBookListener(book -> {
            if (book == null) return;
            boolean success = bookService.updateBook(book);
            if (success) {
                JOptionPane.showMessageDialog(view, "Cập nhật sách thành công!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật sách.");
            }
        });

        view.setDeleteBookListener(id -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn chắc chắn muốn xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = bookService.deleteBook(id);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Xóa sách thành công!");
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(view, "Lỗi khi xóa sách.");
                }
            }
        });

        view.setSearchListener(keyword -> {
            if (keyword == null) return;
            List<BookDTO>  book = bookService.searchBook(keyword);
            if (book == null) return;
            view.setBookTable(book);
        });

        view.setRefreshListener(this::loadBooks);
    }
}
