package org.example.Views;

import org.example.Controllers.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;
    private JButton btnLogin;

    private AuthController controller;

    public LoginFrame() {
        this.controller = new AuthController(this);
        setTitle("Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.WHITE);

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        panel.add(lblError, gbc);

        btnLogin = new JButton("Login");
        gbc.gridy = 3;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> onLoginClicked());

        getContentPane().add(panel);
    }

    private void onLoginClicked() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            setError("Username và password không được để trống.");
            return;
        }

        setError("Đang đăng nhập...");
        btnLogin.setEnabled(false);
        controller.login(username, password);
    }

    public void setError(String message) {
        lblError.setText(message);
    }

    public void refresh() {
        txtUsername.setText("");
        txtPassword.setText("");
        btnLogin.setEnabled(true);
        lblError.setText("Sai tài khoản hoặc mật khẩu !");
    }
}
