package com.company.services;


import com.company.exceptions.DataNotFoundException;
import com.company.exceptions.InvalidParamException;
import com.company.forms.OrderDetailForm;
import com.company.models.OrderDetail;
import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailForm orderDetailForm) throws DataNotFoundException, InvalidParamException;
    OrderDetail getOrderDetailByOrderDetailId(Long orderDetailId) throws DataNotFoundException;
    OrderDetail updateOrderDetailById(Long orderDetailId, OrderDetailForm orderDetailForm) throws DataNotFoundException;
    void deleteOrderDetailById(Long id) throws DataNotFoundException;
    List<OrderDetail> getAllOrdersDetail(Long userId) throws DataNotFoundException;
}
