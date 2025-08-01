package com.example.order_service.service;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;

public interface OrderService {
    Order createOrder(OrderRequest request);
    Order getOrderById(Long id);
}
