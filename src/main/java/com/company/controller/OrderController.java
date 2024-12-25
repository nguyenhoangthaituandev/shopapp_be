package com.company.controller;

import com.company.dtos.OrderDTO;
import com.company.forms.OrderForm;
import com.company.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderForm orderForm,
            BindingResult result
    ){
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderDTO orderDTO=orderService.createOrder(orderForm);
            return ResponseEntity.ok(orderDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUserId( @Valid @PathVariable(name="userId") Long userId){
        try{
            return ResponseEntity.ok("Get All Order By UserId successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderByUserId(
            @Valid @PathVariable(name="id") Long Id,
            @Valid @RequestBody OrderForm orderForm
    ){
        return ResponseEntity.ok("Update Order By UserId successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@Valid @PathVariable(name="id") Long id){
        return ResponseEntity.ok("Delete Order By Id successfully");
    }
}
