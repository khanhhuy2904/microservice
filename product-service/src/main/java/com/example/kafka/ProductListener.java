package com.example.kafka;

import com.example.dto.OrderRequest;
import com.example.entity.Product;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductListener {
    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "order-paid", groupId = "product-group")
    @Transactional
    public void handleOrderPaid(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow();
        if (product.getQuantity() >= request.getQuantity()) {
            product.setQuantity(product.getQuantity() - request.getQuantity());
            productRepository.save(product);
        } else {
            System.out.println("Không đủ hàng");
//            throw new RuntimeException("Không đủ số lượng sản phẩm.");
        }
    }
}

