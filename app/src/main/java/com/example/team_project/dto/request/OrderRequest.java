package com.example.team_project.dto.request;

import java.io.Serializable;
import java.util.List;

public class OrderRequest implements Serializable {
    private String phone;
    private List<OrderItemRequest> orderItems;

    public OrderRequest() {
    }

    public OrderRequest(String phone, List<OrderItemRequest> orderItems) {
        this.phone = phone;
        this.orderItems = orderItems;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "phone='" + phone + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}

