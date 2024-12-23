package com.company.services;

import com.company.dtos.ProductDTO;
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
    Product createProduct(ProductForm productForm) throws DataNotFoundException;

    Product getProductById(Long id) throws DataNotFoundException;

    Page<ProductDTO> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long id, ProductForm productForm) throws DataNotFoundException;

    void deleteProductById(Long id) throws DataNotFoundException;

    boolean existsByName(String name);

    ProductImage createProductImage(String fileNames, MultipartFile file, ProductImageForm productImageForm) throws DataNotFoundException, InvalidParamException, IOException;
}
