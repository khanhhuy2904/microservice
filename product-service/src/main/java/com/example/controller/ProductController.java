package com.example.controller;

import com.example.dto.ProductDTO;
import com.example.dto.UserDTO;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ExcelImportService;
import com.example.service.ProductService;
import com.example.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ExcelImportService excelImportService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/check-user/{userId}")
    public String checkUser(@PathVariable Long userId) {
        return productService.checkUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/deduct")
    public ResponseEntity<Void> deductStock(@PathVariable Long id,
                                            @RequestParam int quantity) {
        try {
            productService.deductStock(id, quantity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/import-excel")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importExcel(file);
            return ResponseEntity.ok("Import thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

}

