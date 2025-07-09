package org.example.Controllers;

import org.example.Models.User;
import org.example.Service.UserService;
import org.example.Views.UserFrame;

import java.util.List;

public class UserController {
    private UserService userService = new UserService();
    private UserFrame view;

    public UserController(UserFrame userManagementFrame) {
       this.view = userManagementFrame;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void addUser(User user) {
        boolean success = userService.addUser(user);
        if (!success) {
            System.err.println("Lỗi thêm user");
        }
    }

    public void updateUser(User user) {
        System.out.println("Check IG: "+user.toString());
        boolean success = userService.updateUser(user);
        if (!success) {
            System.err.println("Lỗi cập nhật user");
        }
    }

    public void deleteUser(Long id) {
        boolean success = userService.deleteUser(id);
        if (!success) {
            System.err.println("Lỗi xóa user");
        }
    }
}
