package com.company.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name="email", length=150)
    private String email;

    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name="status")
    private String status;

    @Column(name="total_price")
    private Float totalPrice;

    @Column(name="shipping_method")
    private String shippingMethod;

    @Column(name="shipping_address")
    private String shippingAddress;

    @Column(name="shipping_date")
    private Date shippingDate;

    @Column(name="tracking_number")
    private String trackingNumber;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="active")
    private Boolean isActive;
}
