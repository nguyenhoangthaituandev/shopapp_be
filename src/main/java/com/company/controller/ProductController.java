package com.company.controller;

import com.company.forms.ProductForm;
import jakarta.validation.Valid;
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
@RequestMapping("api/v1/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> GetAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Get All products here with page = %d limit = %d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByID(@PathVariable(name = "id") Long productID) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Get Product with ID: %d", productID));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @ModelAttribute @Valid ProductForm productForm,
            BindingResult result) throws IOException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        List<MultipartFile> files = productForm.getFiles();
        files = files == null ? new ArrayList<MultipartFile>() : files;
        for (MultipartFile file : files) {
            if(file.getSize()==0){
                continue;
            }
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be image type");
            }

            // save file to thumbnail
            String fileNames = storeFile(file);
            // TODO lưu vào db
        }
        return ResponseEntity.status(HttpStatus.OK).body("Create product successfully:  " + productForm);
    }

    private String storeFile(MultipartFile file) throws IOException {
        // get name và loại bỏ những ký tự thừa
        String fileName = StringUtils.cleanPath((file.getOriginalFilename()));
        // generate ra tên unique
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // tạo ra một đối tượng đại diện folder uploads -> nơi chứa image
        Path uploadDir = Paths.get("uploads");
        // nếu chưa có folder đó thì tạo
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // tạo đường dẫn đầy đủ từ folder tới file image mình mới generate ra
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // file.getInputStream(): Lấy dữ liệu đầu vào từ tệp được tải lên.
        // Files.copy(): Sao chép nội dung của tệp từ luồng đầu vào (InputStream) đến đường dẫn đích (destination).
        // StandardCopyOption.REPLACE_EXISTING: Nếu một tệp với cùng tên đã tồn tại, tệp mới sẽ ghi đè lên tệp cũ.
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductByID(@PathVariable(name = "id") Long productID) {
        return ResponseEntity.status(HttpStatus.OK).body("Delete Product: " + productID);
    }
}