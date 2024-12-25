package com.company.services;

import com.company.dtos.OrderDTO;
import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.OrderForm;

import java.util.List;

public interface IOrderService {
    OrderDTO createOrder(OrderForm orderForm) throws DataNotFoundException, InvalidParamException;
    OrderDTO getOrderById(Long orderId);
    OrderDTO updateOrderById(Long orderId, OrderForm orderForm);
    void deleteOrderById(Long id);
    List<OrderDTO> getAllOrdersByUserId(Long userId);
}
