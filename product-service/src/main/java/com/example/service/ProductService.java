package com.example.service;

import com.example.dto.ProductDTO;

import java.util.Optional;

public interface ProductService {
    String checkUser(Long userId);
    Optional<ProductDTO> getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
}

