package com.company.controller;

import com.company.forms.UserRegisterForm;
import com.company.forms.UserLoginForm;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRegisterForm userRegisterForm,
            BindingResult result){
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userRegisterForm.getPassword().equals(userRegisterForm.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password and retype password are not match");
            }
            return ResponseEntity.ok("Register successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login( @Valid @RequestBody UserLoginForm userLoginForm){
        return ResponseEntity.ok("some token");
    }
}
