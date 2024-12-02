package com.company.forms;

import com.company.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ProductImageForm {

    @Min(value=1,message="Product's id must greater than zero")
    private Long productId;

    @Size(min=5,max=200,message="Image's name between 5 and 200")
    private String imageUrl;
}
