package com.company.services;

import com.company.forms.CategoryForm;
import com.company.models.Category;
import com.company.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{


    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Category createCategory(CategoryForm categoryForm) {
        Category category=Category.builder()
                .name(categoryForm.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) { // Optional nhân tồn tại hoặc không tồn tại
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }

    @Override
    public Category updateCategory(Long id, CategoryForm categoryForm) {
        Category existingCategory=getCategoryById(id);
        existingCategory.setName(categoryForm.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
         categoryRepository.deleteById(id);
    }
}
