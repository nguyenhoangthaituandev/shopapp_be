package com.company.forms;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailForm {
    @Min(value=1,message="Order's Id must be greater than zero")
    private Long orderId;

    @Min(value=1,message="Product's Id must be greater than zero")
    private Long productId;

    @Min(value=0,message="Price must be greater than zero")
    private Float price;

    @Min(value=0,message="Quantity must be greater than zero")
    private Integer quantity;

    @Min(value=0,message="Total money must be greater than zero")
    private Float totalPrice;

    private String color;
}
