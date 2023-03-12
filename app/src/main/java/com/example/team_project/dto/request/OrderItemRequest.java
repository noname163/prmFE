package com.example.team_project.dto.request;

import java.io.Serializable;

public class OrderItemRequest implements Serializable {
    private Integer quantity;
    private Long productId;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Integer quantity, Long productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "quantity=" + quantity +
                ", productId=" + productId +
                '}';
    }
}
