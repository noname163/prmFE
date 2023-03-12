package com.example.team_project.dto.response;

import java.time.LocalDate;

public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double discount;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private String productName;
    private Double price;
    private String productImage;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long id, Integer quantity, Double discount, LocalDate orderDate, LocalDate shipDate, String productName, Double price, String productImage) {
        this.id = id;
        this.quantity = quantity;
        this.discount = discount;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public String toString() {
        return "OrderItemResponse{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", orderDate=" + orderDate +
                ", shipDate=" + shipDate +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", productImage='" + productImage + '\'' +
                '}';
    }
}
