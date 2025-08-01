package com.example.order_service.service.impl;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.ProductResponse;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import com.example.order_service.service.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired
    private OrderEventPublisher orderEventPublisher;
    @Autowired private KafkaTemplate<String, OrderRequest> kafkaTemplate;

    @Autowired private RestTemplate restTemplate;
    @Value("${external.payment-service.url}") private String paymentUrl;
    @Value("${external.product-service.url}") private String productUrl;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest request) {
        ProductResponse product = restTemplate.getForObject(productUrl + request.getProductId(), ProductResponse.class);
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = new Order(request.getId(), request.getUserId(), request.getProductId(), request.getQuantity(), total, "CREATED");
        order = orderRepository.save(order);
        System.out.println("Order created");
        request =  orderMapper.toDTO(order);


        orderEventPublisher.sendOrderEvent(request);

        return order;
    }

    @KafkaListener(topics = "payment-result", groupId = "order-group")
    @Transactional
    public void handlePaymentResult(OrderRequest request) {
        Order order = orderMapper.toEntity(request);
        orderRepository.findById(order.getId()).orElseThrow();
        order.setStatus(request.getStatus());
        System.out.println(order.getTotalPrice());
        orderRepository.save(order);

        if ("PAID".equals(request.getStatus())) {
            kafkaTemplate.send("order-paid", request);
            System.out.println("✅ Order marked as PAID and event sent: " + order.getId());
        } else {
            System.out.println("❌ Order payment failed: " + order.getId());
        }
    }
}
