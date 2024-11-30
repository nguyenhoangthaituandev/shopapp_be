package com.company.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class UserRegisterForm {
    private String fullName;

    @NotBlank(message="Phone number is required")
    private String phoneNumber;

    private String address;

    @NotBlank(message="Password can not be blank")
    private String password;

    private String retypePassword;

    private Date dateOfBirth;

    private Integer facebookAccountId;

    private Integer googleAccountId;

    @NotNull(message="Role'id  is required")
    private Long roleId;
}
