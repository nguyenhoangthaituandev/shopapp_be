package com.company.forms;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductForm {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 200, message = "Name must between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal 0")
    private Float price;

    private String thumbnail;

    private String description;

    private Long categoryId;

    private List<MultipartFile> files;
}
