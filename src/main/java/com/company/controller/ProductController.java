package com.company.controller;

import com.company.constant.Constant;
import com.company.dtos.ProductDTO;
import com.company.dtos.ProductListDTO;
import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.ProductForm;
import com.company.forms.ProductImageForm;
import com.company.models.Product;
import com.company.models.ProductImage;
import com.company.services.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(
            @RequestBody @Valid ProductForm productForm,
            BindingResult result) throws IOException, DataNotFoundException, InvalidParamException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        Product product = productService.createProduct(productForm);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable(name = "id") Long productId,
                                          @RequestParam("files") List<MultipartFile> files
    ) throws Exception {
        Product product = productService.getProductById(productId);

        files = files == null ? new ArrayList<MultipartFile>() : files;
        if (files.size() >= Constant.MAXIMUM_IMAGES) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can upload maximum +" + Constant.MAXIMUM_IMAGES + " images");
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Image is null");
            }
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be image type");
            }

            String fileNames = getFileName(file);

            try {
                ProductImage productImage = productService.createProductImage(fileNames, file,
                        ProductImageForm.builder()
                                .productId(product.getId())
                                .imageUrl(fileNames)
                                .build());
                productImages.add(productImage);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            }


        }
        return ResponseEntity.status(HttpStatus.OK).body(productImages);
    }

    @GetMapping
    public ResponseEntity<ProductListDTO> GetAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductDTO> productPage = productService.getAllProducts(pageRequest);

        int totalPages = productPage.getTotalPages();
        List<ProductDTO> products = productPage.getContent();
        return ResponseEntity.ok(ProductListDTO.builder()
                .product(products)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable(name = "id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Get Product with ID: %d", productId));
    }


    public String getFileName(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image file format");
        }
        // get name và loại bỏ những ký tự thừa
        String fileName = StringUtils.cleanPath((file.getOriginalFilename()));
        // generate ra tên unique
        return UUID.randomUUID().toString() + "_" + fileName;


    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable(name = "id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body("Delete Product: " + productId);
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts(){
        Faker faker=new Faker();
        for(int i=0;i<1000;i++){
            String productName=faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }

            ProductForm productForm=ProductForm.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(0,90_000_000))
                    .thumbnail("")
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(1,4))
                    .build();

            try {
                productService.createProduct(productForm);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products created successfully");
    }
}