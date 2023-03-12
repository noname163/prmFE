package com.example.team_project.dto.response;

import com.example.team_project.constant.EOrderStatus;

import java.io.Serializable;

public class OrderResponse implements Serializable {
    private Long id;
    private String orderDate;
    private String requiredDate;
    private String shippedDate;
    private EOrderStatus orderStatus;
    private String name;
    private String store;
    private String staff;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String orderDate, String requiredDate, String shippedDate, EOrderStatus orderStatus, String name, String store, String staff) {
        this.id = id;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.orderStatus = orderStatus;
        this.name = name;
        this.store = store;
        this.staff = staff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public EOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", requiredDate=" + requiredDate +
                ", shippedDate=" + shippedDate +
                ", orderStatus=" + orderStatus +
                ", name='" + name + '\'' +
                ", store='" + store + '\'' +
                ", staff='" + staff + '\'' +
                '}';
    }
}
