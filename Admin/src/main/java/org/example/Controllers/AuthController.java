package org.example.Controllers;

import org.example.DTO.LoginRequest;
import org.example.Service.AuthService;
import org.example.Views.HomeFrame;
import org.example.Views.LoginFrame;

import javax.swing.*;

public class AuthController {
    private LoginFrame view;
    private AuthService authService = AuthService.getInstance();

    public AuthController(LoginFrame view) {
        this.view = view;
        this.authService = authService;
    }

    public void login(String username, String password) {
        new Thread(() -> {
            try {
                boolean success = authService.login(new LoginRequest(username, password));
                if (success) {
                    view.dispose();
                    HomeFrame homeFrame = new HomeFrame();
                    homeFrame.setVisible(true);
                } else {
                    view.refresh();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }).start();
    }

    public String getJwtToken() {
        return authService.getJwtToken();
    }
}
