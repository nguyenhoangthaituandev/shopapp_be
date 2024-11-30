package com.company.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String categoryID;

    private List<MultipartFile> files;
}
