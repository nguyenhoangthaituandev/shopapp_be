package com.company.controller;

import com.company.forms.ProductForm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping
    public ResponseEntity<String> GetAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Get All products here with page = %d limit = %d",page,limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByID(@PathVariable(name = "id") Long productID){
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Get Product with ID: %d",productID));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestBody @Valid ProductForm productForm,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages =result.getFieldErrors()
                    .stream()
                    .map(FieldError:: getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Create product "+productForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable(name = "id") Long productID){
        return ResponseEntity.status(HttpStatus.OK).body("Delete Product: "+productID);
    }
}
