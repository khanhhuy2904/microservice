package com.example.service;

import com.example.dto.ProductDTO;
import com.example.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    String checkUser(Long userId);
    ProductDTO getProductById(Long id);
    Product createProduct(Product product);
    void deductStock(Long productId, int quantity);
    List<ProductDTO> getAllProducts();
}

