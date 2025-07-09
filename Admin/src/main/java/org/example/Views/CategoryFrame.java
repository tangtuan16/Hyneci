package org.example.Views;

import org.example.Controllers.CategoryController;
import org.example.Models.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CategoryFrame extends JFrame {
    private final JTextField txtName = new JTextField(20);
    private final JTextArea txtDescription = new JTextArea(3, 20);
    private final CategoryController controller = new CategoryController();

    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTable table = new JTable(tableModel);

    private long selectedCategoryId = -1;

    public CategoryFrame() {
        setTitle("Quản lý danh mục");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadCategoriesToTable();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin danh mục"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên danh mục:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mô tả:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        panel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");

        btnAdd.addActionListener(this::onAddClick);
        btnUpdate.addActionListener(this::onUpdateClick);
        btnDelete.addActionListener(this::onDeleteClick);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JScrollPane createTablePanel() {
        tableModel.addColumn("ID");
        tableModel.addColumn("Tên danh mục");
        tableModel.addColumn("Mô tả");

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                onTableRowSelect();
            }
        });

        return new JScrollPane(table);
    }

    private void loadCategoriesToTable() {
        List<Category> categories = controller.getAllCategories();
        tableModel.setRowCount(0);
        for (Category cat : categories) {
            tableModel.addRow(new Object[]{cat.getId(), cat.getName(), cat.getDescription()});
        }
    }

    private void onAddClick(ActionEvent e) {
        String name = txtName.getText().trim();
        String desc = txtDescription.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống.");
            return;
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(desc);
        boolean message = controller.handleAddCategory(category);
        if (message) {
            JOptionPane.showMessageDialog(this, "Done!");
        } else {
            JOptionPane.showMessageDialog(this, "Error");
        }
        resetForm();
        loadCategoriesToTable();
    }

    private void onUpdateClick(ActionEvent e) {
        if (selectedCategoryId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục để cập nhật.");
            return;
        }

        String name = txtName.getText().trim();
        String desc = txtDescription.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống.");
            return;
        }

        Category category = new Category();
        category.setId(selectedCategoryId);
        category.setName(name);
        category.setDescription(desc);
        boolean message = controller.handleUpdateCategory(category);
        if (message) {
            JOptionPane.showMessageDialog(this, "Done!");
        } else{
            JOptionPane.showMessageDialog(this, "Error");
        }
        resetForm();
        loadCategoriesToTable();
    }

    private void onDeleteClick(ActionEvent e) {
        if (selectedCategoryId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục để xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa danh mục này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean message = controller.handleDeleteCategory(selectedCategoryId);
            if (message) {
                JOptionPane.showMessageDialog(this, "Done !");
            } else {
                JOptionPane.showMessageDialog(this, "Error !");
            }
            resetForm();
            loadCategoriesToTable();
        }
    }

    private void onTableRowSelect() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedCategoryId = (long) tableModel.getValueAt(selectedRow, 0);
            txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtDescription.setText(tableModel.getValueAt(selectedRow, 2).toString());
        }
    }

    private void resetForm() {
        txtName.setText("");
        txtDescription.setText("");
        selectedCategoryId = -1;
        table.clearSelection();
    }
}
