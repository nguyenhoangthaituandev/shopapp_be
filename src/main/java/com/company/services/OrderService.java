package com.company.services;

import com.company.dtos.OrderDTO;
import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.OrderForm;

import com.company.models.Order;
import com.company.models.OrderStatus;
import com.company.models.User;
import com.company.repositories.OrderRepository;
import com.company.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO createOrder(OrderForm orderForm) throws DataNotFoundException, InvalidParamException {
        // check user exist
        User user = userRepository
                .findById(orderForm.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can not found user with ID: " + orderForm.getUserId()));

        // convert orderForm -> Order using modelmapper
        // ignore properties Id in Order class
        modelMapper.typeMap(OrderForm.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        Order order = new Order();
        modelMapper.map(orderForm, order); // mapping orderForm -> order

        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        Date shippingDate = orderForm.getShippingDate() == null ? new Date() : orderForm.getShippingDate();
        if (shippingDate.after(new Date())) {
            throw new InvalidParamException("Shipping Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setIsActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class); // mapping order -> orderDTO
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return null;
    }

    @Override
    public OrderDTO updateOrderById(Long orderId, OrderForm orderForm) {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {

    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return List.of();
    }
}
