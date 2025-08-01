package com.example.dto;

import java.math.BigDecimal;

public class OrderRequest {
    private Long Id;
    private Long userId;
    private Long productId;
    private int quantity;
    private String status;
    private BigDecimal totalPrice;


    public OrderRequest( Long Id, Long userId, Long productId, int quantity, String status) {
        this.Id = Id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;

    }
    public OrderRequest() {}

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}