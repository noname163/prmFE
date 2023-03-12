package com.example.team_project.models;

public class Cart {
    String cartId;
    String currentTime;
    String currentDate;
    String productName;
    String productPrice;
    String totalQuantity;
    String productImage;
    Long productId;
    int totalPrice;

    public Cart() {
    }

    public Cart(String currentTime, String currentDate, String productName, String productPrice, String totalQuantity, int totalPrice, Long productId) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }

    public Cart(String currentTime, String currentDate, String productName, String productPrice, String totalQuantity, String productImage, int totalPrice, Long productId) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.productImage = productImage;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }



    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productId=" + productId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
