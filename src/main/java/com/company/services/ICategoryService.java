package com.company.services;

import com.company.forms.CategoryForm;
import com.company.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryForm category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long id, CategoryForm category);

    void deleteCategory(Long id);
}
