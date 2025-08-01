package com.example.payment_service.kafka;


import com.example.payment_service.dto.OrderRequest;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.service.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentListener {
    @Autowired
    private KafkaTemplate<String, OrderRequest> kafkaTemplate;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    private OrderRequest currentOrderRequest;

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    @Transactional
    public void processPayment(OrderRequest request) {
       this.currentOrderRequest = request;
    }

    public void sendPaymentResult(PaymentRequest paymentRequest) {
        if (paymentRequest.getStatus().equals("SUCCESS")) {
            currentOrderRequest.setStatus("PAID");
            kafkaTemplate.send("payment-result",currentOrderRequest );
        } else {
            paymentRequest.setStatus("FAILED");
            currentOrderRequest.setStatus("FAILED");
            kafkaTemplate.send("payment-result", currentOrderRequest );
        }
        Payment payment = paymentMapper.toEntity(paymentRequest);
        paymentRepository.save(payment);
    }
}

