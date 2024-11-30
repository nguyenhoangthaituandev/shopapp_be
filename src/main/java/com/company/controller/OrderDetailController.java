package com.company.controller;

import com.company.forms.OrderDetailForm;
import jakarta.validation.Valid;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
public class OrderDetailController {
    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailForm orderDetailForm
            ){
        return ResponseEntity.ok("Create Order Detail Successfully");
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> GetOrderDetailsByOrderId(@Valid @PathVariable(name="id") Long orderId){
        return ResponseEntity.ok("Get All Order Details By Order's Id Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetailById(@Valid @PathVariable(name="id") Long orderDetailId,
                                                   @RequestBody OrderDetailForm orderDetailForm){
        return ResponseEntity.ok(String.format("Update Order Details By Id: %d Successfully as " + orderDetailForm,orderDetailId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteOrderDetailById(@Valid @PathVariable(name="id") Long orderDetailId){
        return ResponseEntity.noContent().build();
    }
}
