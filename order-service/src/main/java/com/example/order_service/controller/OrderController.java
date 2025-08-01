package com.example.order_service.controller;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }
}

