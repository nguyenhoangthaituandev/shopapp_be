package com.company.dtos;

import com.company.models.Order;
import com.company.models.OrderDetail;
import com.company.models.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Long orderDetailId;

    private Long orderId;


    private Long productId;


    private Float price;


    private Integer quantity;


    private Float totalPrice;

    private String color;

    public static OrderDetailDTO fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .orderDetailId(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .color(orderDetail.getColor())
                .build();
    }
}
