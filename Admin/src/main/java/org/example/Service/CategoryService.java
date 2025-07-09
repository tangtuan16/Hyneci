package org.example.Service;

import org.example.Models.Category;
import org.example.Utils.ApiHelper;

import java.util.List;

public class CategoryService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static AuthService authService;

    public CategoryService() {
        authService = AuthService.getInstance();
    }

    public List<Category> getCategories() {
        String token = authService.getJwtToken();
        return ApiHelper.getList(BASE_URL + "/categories", Category[].class, token);
    }

    public boolean addCategory(Category category) {
        String token = authService.getJwtToken();
        return ApiHelper.post(BASE_URL + "/categories", category, token);
    }

    public boolean deleteCategory(long selectedCategoryId) {
        String token = authService.getJwtToken();
        return ApiHelper.delete(BASE_URL + "/categories/" + selectedCategoryId, token);
    }

    public boolean updateCategory(Category category) {
        String token = authService.getJwtToken();
        System.out.println("Category: " + category.toString());
        System.out.println("Token: " + token);
        return ApiHelper.put(BASE_URL + "/categories/" + category.getId(), category, token);
    }
}
