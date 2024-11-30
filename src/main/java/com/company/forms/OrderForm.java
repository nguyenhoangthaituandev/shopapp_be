package com.company.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderForm {
    @Min(value=1,message="User's Id must be greater than zero")
    private Long userId;

    private String fullName;

    private String email;

    @NotBlank(message="Phone number is required")
    @Size(min=5,message="Phone number must be at least 5 characters")
    private String phoneNumber;

    private String address;

    private String note;

    @Min(value=0,message="Total money must be greater than zero")
    private Float totalMoney;

    private String shippingMethod;

    private String shippingAddress;

    private String paymentMethod;


}
