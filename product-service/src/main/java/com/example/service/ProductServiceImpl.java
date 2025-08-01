package com.example.service;

import com.example.dto.ProductDTO;
import com.example.dto.UserDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
    @Cacheable(value = "products")
    public List<ProductDTO> getAllProducts() {
        System.out.println("Fetching products from DB...");
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
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
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deductStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}


