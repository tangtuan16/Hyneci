package org.example.Views;

import org.example.Controllers.BookController;
import org.example.Controllers.CategoryController;
import org.example.DTO.BookDTO;
import org.example.Models.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BookFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtTitle, txtAuthor, txtDescription, txtStock, txtImageUrl, txtSearch;
    private JComboBox<String> cmbBook;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnSearch;

    private Consumer<BookDTO> onAddBook;
    private Consumer<BookDTO> onUpdateBook;
    private Consumer<String> onSearchBook;
    private Consumer<Integer> onDeleteBook;
    private Runnable onRefresh;

    private Map<String, Integer> categoryMap = new HashMap<>();
    private Integer selectedCategoryId = 1;
    private boolean isCategoryListenerAdded = false;

    private BookController bookController;
    private CategoryController categoryController;


    public BookFrame() {
        setTitle("Quản lý sách");
        setSize(800, 500);
        setLocationRelativeTo(null);
        initUI();
        setUIFont(this.getContentPane(), new Font("Arial", Font.PLAIN, 14));
        this.bookController = new BookController(this);
        this.categoryController = new CategoryController(this);
    }

    private void setUIFont(Component comp, Font font) {
        comp.setFont(font);
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                setUIFont(child, font);
            }
        }
    }

    private void initUI() {
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm");
        searchPanel.add(new JLabel("Tìm sách:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Bảng
        tableModel = new DefaultTableModel(new Object[]{"ID", "Tiêu đề", "Tác giả", "Mô tả", "Danh mục", "Số lượng", "Ảnh"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitle = new JTextField(20);
        txtAuthor = new JTextField(20);
        txtDescription = new JTextField(20);
        txtStock = new JTextField(20);
        txtImageUrl = new JTextField(20);
        cmbBook = new JComboBox<>();

        String[] labels = {"Tiêu đề:", "Tác giả:", "Mô tả:", "Danh mục:", "Số lượng:", "Ảnh URL:"};
        JTextField[] fields = {txtTitle, txtAuthor, txtDescription, txtStock, txtImageUrl};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weightx = 1;

            if (i == 3) {
                formPanel.add(cmbBook, gbc);
            } else {
                formPanel.add(fields[i < 3 ? i : i - 1], gbc);
            }
        }

        // Các nút
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Làm mới");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        // Layout tổng thể
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        cp.add(searchPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        cp.add(formPanel, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        cp.add(scrollPane, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        cp.add(buttonPanel, gbc);

        // Sự kiện nút
        btnAdd.addActionListener(e -> {
            if (onAddBook != null) onAddBook.accept(getBookFromForm());
        });

        btnUpdate.addActionListener(e -> {
            if (onUpdateBook != null) onUpdateBook.accept(getBookFromFormWithId());
        });

        btnDelete.addActionListener(e -> {
            int id = getSelectedBookId();
            if (id != -1 && onDeleteBook != null) onDeleteBook.accept(id);
        });

        btnRefresh.addActionListener(e -> {
            if (onRefresh != null) onRefresh.run();
        });

        btnSearch.addActionListener(e -> {
            onSearchBook.accept(getSearchFromBoook());
        });

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
    }

    public void setBookTable(List<BookDTO> books) {
        tableModel.setRowCount(0);
        for (BookDTO b : books) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getDescription(),
                    b.getCategoryName(),
                    b.getStock(),
                    b.getImageUrl()
            });
        }
        tableModel.fireTableDataChanged();
    }

    private BookDTO getBookFromForm() {
        BookDTO book = new BookDTO();
        book.setTitle(txtTitle.getText());
        book.setAuthor(txtAuthor.getText());
        book.setDescription(txtDescription.getText());
        try {
            book.setStock(Integer.parseInt(txtStock.getText()));
        } catch (NumberFormatException e) {
            book.setStock(0);
        }
        book.setImageUrl(txtImageUrl.getText());

        String selected = (String) cmbBook.getSelectedItem();
        if (selected == null && cmbBook.getItemCount() > 0) {
            selected = (String) cmbBook.getItemAt(0);
        }
        if (selected != null && categoryMap.containsKey(selected)) {
            book.setCategoryId(Long.valueOf(categoryMap.get(selected)));
            book.setCategoryName(selected);
        }
        return book;
    }

    private String getSearchFromBoook() {
        String search = txtSearch.getText().trim();
        if (search.length() == 0) {
            return null;
        }
        return search;
    }

    private BookDTO getBookFromFormWithId() {
        int id = getSelectedBookId();
        if (id == -1) return null;
        BookDTO book = getBookFromForm();
        book.setId(id);
        return book;
    }

    private int getSelectedBookId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return -1;
        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtTitle.setText(tableModel.getValueAt(row, 1).toString());
        txtAuthor.setText(tableModel.getValueAt(row, 2).toString());
        txtDescription.setText(tableModel.getValueAt(row, 3).toString());
        cmbBook.setSelectedItem(tableModel.getValueAt(row, 4).toString());
        txtStock.setText(tableModel.getValueAt(row, 5).toString());
        txtImageUrl.setText(tableModel.getValueAt(row, 6).toString());
    }

    public void setAddBookListener(Consumer<BookDTO> listener) {
        this.onAddBook = listener;
    }

    public void setUpdateBookListener(Consumer<BookDTO> listener) {
        this.onUpdateBook = listener;
    }

    public void setDeleteBookListener(Consumer<Integer> listener) {
        this.onDeleteBook = listener;
    }

    public void setRefreshListener(Runnable listener) {
        this.onRefresh = listener;
    }

    public void setSearchListener(Consumer<String> listener) {
        this.onSearchBook = listener;
    }

    public void setCategoryCombo(List<Category> categories) {
        cmbBook.removeAllItems();
        categoryMap.clear();
        for (Category c : categories) {
            cmbBook.addItem(c.getName());
            categoryMap.put(c.getName(), Math.toIntExact(c.getId()));
        }

        if (!isCategoryListenerAdded) {
            cmbBook.addActionListener(e -> {
                String selectedName = (String) cmbBook.getSelectedItem();
                selectedCategoryId = categoryMap.get(selectedName);
                System.out.println("Selected category id: " + selectedCategoryId);
            });
            isCategoryListenerAdded = true;
        }
    }

    public void refreshInput() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtDescription.setText("");
        txtImageUrl.setText("");
        txtStock.setText("");
        txtImageUrl.setText("");
    }
}
