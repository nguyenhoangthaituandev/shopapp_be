package com.company.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO{
    private Long id;
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String note;
    private Date orderDate;
    private String status;
    private Float totalPrice;
    private String shippingMethod;
    private String shippingAddress;
    private Date shippingDate;
    private String trackingNumber;
    private String paymentMethod;
    private Boolean isActive;

}
