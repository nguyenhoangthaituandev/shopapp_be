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
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
    public List<OrderDTO> getAllOrdersByUserId(Long userId) throws DataNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new DataNotFoundException("Can not found user with ID: " + userId);
        }

        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new DataNotFoundException("No orders found for user with ID " + userId);
        }
        Type listType = new TypeToken<List<OrderDTO>>() {
        }.getType();
        return modelMapper.map(orders, listType);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Can not found order with ID: " + orderId));

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrderById(Long orderId, OrderForm orderForm) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Can not found order with ID: " + orderId));
        User user = userRepository.findById(orderForm.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can not found user with ID: " + orderForm.getUserId()));

        modelMapper.typeMap(OrderForm.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderForm, order);
        order.setUser(user);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public void deleteOrderById(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not found order with ID: " + id));

        order.setIsActive(false);
        orderRepository.save(order);

    }


}
