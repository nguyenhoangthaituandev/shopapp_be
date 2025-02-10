package com.company.controller;

import com.company.dtos.OrderDetailDTO;
import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.OrderDetailForm;
import com.company.models.OrderDetail;
import com.company.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailForm orderDetailForm
            ){
        try {
            OrderDetail orderDetail=orderDetailService.createOrderDetail(orderDetailForm);
            return ResponseEntity.ok().body(OrderDetailDTO.fromOrderDetail(orderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<?> GetOrderDetailsById(@Valid @PathVariable(name="orderDetailId") Long orderDetailId){
        try {
            OrderDetail orderDetail=orderDetailService.getOrderDetailByOrderDetailId(orderDetailId);
            return ResponseEntity.ok().body(modelMapper.map(orderDetail,OrderDetailDTO.class));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> GetOrderDetailsByOrderId(@Valid @PathVariable(name="id") Long orderId){
        try {
            List<OrderDetail> orderDetails=orderDetailService.getAllOrdersDetail(orderId);
            List<OrderDetailDTO> orderDetailDTOS=orderDetails
                    .stream()
                    .map(OrderDetailDTO::fromOrderDetail)
                    .toList();
            return ResponseEntity.ok().body(orderDetailDTOS);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetailById(
            @Valid @PathVariable(name="id") Long orderDetailId,
            @RequestBody OrderDetailForm orderDetailForm){
        try {
           OrderDetail orderDetail= orderDetailService.updateOrderDetailById(orderDetailId,orderDetailForm);
            return ResponseEntity.ok().body(modelMapper.map(orderDetail,OrderDetailDTO.class));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteOrderDetailById(@Valid @PathVariable(name="id") Long orderDetailId){
        try {
             orderDetailService.deleteOrderDetailById(orderDetailId);
            return ResponseEntity.ok().body("Delete successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
