package com.company.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginForm {
    @NotBlank(message="Phone number is required")
    private String phoneNumber;

    @NotBlank(message="Password can not be blank")
    private String password;
}
