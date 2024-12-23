package com.company.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListDTO {
    private List<ProductDTO> products;
    private int totalPages;
}
