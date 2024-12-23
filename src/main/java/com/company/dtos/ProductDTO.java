package com.company.dtos;

import com.company.models.Product;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO extends BaseDTO {

    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    private Long categoryId;

    public static ProductDTO fromProduct(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        return productDTO;
    }
}
