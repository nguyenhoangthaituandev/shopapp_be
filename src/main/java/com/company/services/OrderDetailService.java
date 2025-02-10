package com.company.services;

import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.OrderDetailForm;
import com.company.models.Order;
import com.company.models.OrderDetail;
import com.company.models.Product;
import com.company.repositories.OrderDetailRepository;
import com.company.repositories.OrderRepository;
import com.company.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailForm orderDetailForm) throws DataNotFoundException, InvalidParamException {
        // check orderId exists
        Order order=orderRepository.findById(orderDetailForm.getOrderId())
                .orElseThrow(()->new DataNotFoundException("Can not find Order with id: "+orderDetailForm.getOrderId()));
        // check productId exists
        Product product=productRepository.findById(orderDetailForm.getProductId())
                .orElseThrow(()->new DataNotFoundException("Can not find Product with id: "+orderDetailForm.getProductId()));
        // create OrderDetail
        OrderDetail orderDetail=OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailForm.getPrice())
                .quantity(orderDetailForm.getQuantity())
                .totalPrice(orderDetailForm.getTotalPrice())
                .color(orderDetailForm.getColor())
                .build();
        // save to db
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailByOrderDetailId(Long orderDetailId) throws DataNotFoundException {
        return orderDetailRepository.findById(orderDetailId)
                .orElseThrow(()-> new DataNotFoundException("Can not found Order Detail Id: "+orderDetailId));
    }

    @Override
    public OrderDetail updateOrderDetailById(Long orderDetailId, OrderDetailForm orderDetailForm) throws DataNotFoundException {
        // check orderDetail exists
        OrderDetail orderDetail=orderDetailRepository.findById(orderDetailId)
                .orElseThrow(()-> new DataNotFoundException("Can not found OrderDetail with id: "+orderDetailId));

        // check Order exists
        Order order=orderRepository.findById(orderDetailForm.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Can not found Order with id: "+orderDetailForm.getOrderId()));

        // check product exists
        Product product=productRepository.findById(orderDetailForm.getProductId())
                .orElseThrow(()->new DataNotFoundException("Can not find Product with id: "+orderDetailForm.getProductId()));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(orderDetailForm.getPrice());
        orderDetail.setQuantity(orderDetailForm.getQuantity());
        orderDetail.setTotalPrice(orderDetailForm.getTotalPrice());
        orderDetail.setColor(orderDetailForm.getColor());

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetailById(Long id) throws DataNotFoundException {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getAllOrdersDetail(Long orderId) throws DataNotFoundException {
        return orderDetailRepository.findByOrderId(orderId);

    }
}
