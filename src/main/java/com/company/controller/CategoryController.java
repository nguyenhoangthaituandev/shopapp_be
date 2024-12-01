package com.company.controller;

import com.company.forms.CategoryForm;
import com.company.models.Category;
import com.company.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @RequestBody @Valid CategoryForm categoryForm,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryForm);
        return ResponseEntity.ok("Insert category successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Category>> GetAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable(name = "id") Long categoryId,
                                                 @RequestBody @Valid CategoryForm categoryForm) {
        categoryService.updateCategory(categoryId,categoryForm);
        return ResponseEntity.ok("Update category successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Delete category successfully!");
    }
}
