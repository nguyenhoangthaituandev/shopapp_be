package com.company.dtos;

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
}
