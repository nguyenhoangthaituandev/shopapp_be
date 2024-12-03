package com.company.services;

import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.ProductForm;
import com.company.forms.ProductImageForm;
import com.company.models.Product;
import com.company.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {
    public Product createProduct(ProductForm productForm) throws DataNotFoundException;

    Product getProductById(Long id) throws DataNotFoundException;

    Page<Product> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long id, ProductForm productForm) throws DataNotFoundException;

    void deleteProduct(Long id);

    boolean existsByName(String name);
    public ProductImage createProductImage(String fileNames, MultipartFile file, ProductImageForm productImageForm) throws DataNotFoundException, InvalidParamException, IOException;
}
