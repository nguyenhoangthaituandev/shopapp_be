package com.company.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_details")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="price", nullable = false)
    private Float price;

    @Column(name="quantity", nullable = false)
    private Integer quantity;

    @Column(name="total_price", nullable = false)
    private Float totalPrice;

    private String color;
}
