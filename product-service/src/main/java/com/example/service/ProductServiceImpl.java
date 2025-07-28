package com.example.service;

import com.example.dto.ProductDTO;
import com.example.dto.UserDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate,
                              @Value("${external.user-service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    @Override
    public String checkUser(Long userId) {
        String url = userServiceUrl + userId;
        System.out.println(">>> Calling URL: " + url);

        try {
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "Found user: " + response.getBody().getName();
            }
        } catch (Exception e) {
            return "User not found or error.";
        }
        return "Something went wrong.";
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }
}


