package org.example.Controllers;

import org.example.Models.Category;
import org.example.Service.CategoryService;
import org.example.Views.BookFrame;

import java.util.List;

public class CategoryController {

    private CategoryService categoryService;
    private BookFrame view;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public CategoryController(BookFrame view) {
        this.view = view;
        this.categoryService = new CategoryService();
        loadCategories();
    }

    public void loadCategories() {
        List<Category> categories = categoryService.getCategories();
        view.setCategoryCombo(categories);
    }

    public boolean handleAddCategory(Category category) {
        return categoryService.addCategory(category);
    }

    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }

    public boolean handleDeleteCategory(long selectedCategoryId) {
        return categoryService.deleteCategory(selectedCategoryId);
    }

    public boolean handleUpdateCategory(Category category) {
        boolean result = categoryService.updateCategory(category);
        System.out.println("Resuilt: " + result);
        return result;
    }
}
