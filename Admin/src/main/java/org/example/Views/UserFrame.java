package org.example.Views;

import org.example.Controllers.UserController;
import org.example.Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private UserController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtId, txtUsername, txtFullName, txtEmail, txtPhone, txtAvatar;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;

    private List<User> users;

    public UserFrame() {
        setTitle("Quản lý User");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        initComponents();
        this.controller = new UserController(this);
        refreshUserList();
    }

    private void initComponents() {
        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin người dùng"));
        txtId = new JTextField(); txtId.setEnabled(false);
        txtUsername = new JTextField();
        txtFullName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtAvatar = new JTextField();
        cbRole = new JComboBox<>(new String[]{"ROLE_USER", "ROLE_ADMIN"});

        formPanel.add(new JLabel("ID:")); formPanel.add(txtId);
        formPanel.add(new JLabel("Username:")); formPanel.add(txtUsername);
        formPanel.add(new JLabel("Full Name:")); formPanel.add(txtFullName);
        formPanel.add(new JLabel("Email:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Phone:")); formPanel.add(txtPhone);
        formPanel.add(new JLabel("Avatar URL:")); formPanel.add(txtAvatar);
        formPanel.add(new JLabel("Role:")); formPanel.add(cbRole);

        // Bảng hiển thị user
        String[] columns = {"ID", "Username", "Full Name", "Email", "Phone", "Avatar", "Role"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        // Layout tổng thể
        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                txtUsername.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                txtFullName.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                txtEmail.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                txtPhone.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                txtAvatar.setText(String.valueOf(tableModel.getValueAt(row, 5)));
                cbRole.setSelectedItem(tableModel.getValueAt(row, 6));
            }
        });

        // Sự kiện nút
        btnAdd.addActionListener(e -> {
            User u = getUserFromForm();
            if (u == null) return;
            controller.addUser(u);
            refreshUserList();
            clearForm();
        });

        btnUpdate.addActionListener(e -> {
            User u = getUserFromForm();
            if (u == null) return;
            controller.updateUser(u);
            refreshUserList();
            clearForm();
        });

        btnDelete.addActionListener(e -> {
            String idStr = txtId.getText();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chọn user để xóa");
                return;
            }
            Long id = Long.parseLong(idStr);
            controller.deleteUser(id);
            refreshUserList();
            clearForm();
        });

        btnClear.addActionListener(e -> clearForm());

        btnRefresh.addActionListener(e -> refreshUserList());
    }

    private User getUserFromForm() {
        String idStr = txtId.getText().trim();
        String username = txtUsername.getText().trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String avatar = txtAvatar.getText().trim();
        String role = (String) cbRole.getSelectedItem();

        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username và Email không được để trống");
            return null;
        }

        Long id = null;
        if (!idStr.isEmpty()) {
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID không hợp lệ");
                return null;
            }
        }

        String password = null;
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1 && selectedRow < users.size()) {
            password = users.get(selectedRow).getPassword();
        } else {
            password = "default";
        }

        return new User(id, username, password, fullName, email, phone, avatar, role);
    }

    private void refreshUserList() {
        users = controller.getAllUsers();
        loadDataToTable();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        for (User u : users) {
            Object[] row = {
                    u.getId(),
                    u.getUsername(),
                    u.getFullName(),
                    u.getEmail(),
                    u.getPhone(),
                    u.getAvatar(),
                    u.getRole()
            };
            tableModel.addRow(row);
        }
        tableModel.fireTableDataChanged();
    }

    private void clearForm() {
        txtId.setText("");
        txtUsername.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAvatar.setText("");
        cbRole.setSelectedIndex(0);
        table.clearSelection();
    }
}
