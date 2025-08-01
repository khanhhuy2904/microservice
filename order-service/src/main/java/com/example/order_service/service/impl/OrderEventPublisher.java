package com.example.order_service.service.impl;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.service.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    @Autowired
    private KafkaTemplate<String, OrderRequest> kafkaTemplate;
    @Value("${kafka.topic.order-events}")
    private String topic;
    @Autowired
    private OrderMapper orderMapper;

    public void sendOrderEvent(OrderRequest request) {
        Order order = orderMapper.toEntity(request);
        kafkaTemplate.send(topic, request);
        System.out.println("✅ Order event sent: " + order.getId() + " - " + order.getStatus() + " - " + order.getTotalPrice());
    }
}
