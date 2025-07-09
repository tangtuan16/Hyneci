package org.example.Service;

import org.example.Models.User;
import org.example.Utils.ApiHelper;

import java.util.List;

public class UserService {
    private static final String BASE_URL = "http://localhost:8080/api/users";

    private AuthService authService;

    public UserService() {
        this.authService = AuthService.getInstance();
    }

    public List<User> getAllUsers() {
        String token = authService.getJwtToken();
        return ApiHelper.getList(BASE_URL, User[].class, token);
    }

    public boolean addUser(User user) {
        String token = authService.getJwtToken();
        return ApiHelper.post(BASE_URL, user, token);
    }

    public boolean updateUser(User user) {
        if (user.getId() == null) return false;
        String token = authService.getJwtToken();
        String url = BASE_URL + "/" + user.getId();
        return ApiHelper.put(url, user, token);
    }

    public boolean deleteUser(Long id) {
        String token = authService.getJwtToken();
        String url = BASE_URL + "/" + id;
        return ApiHelper.delete(url, token);
    }
}
