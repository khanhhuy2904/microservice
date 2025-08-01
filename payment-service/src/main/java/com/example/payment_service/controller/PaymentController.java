package com.example.payment_service.controller;

import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequest request) {
        System.out.println("Sending payment: " + request);
        paymentService.createPayment(request);
        return ResponseEntity.ok().build();
    }
}
