package com.example.team_project.service;

import com.example.team_project.dto.request.OrderRequest;
import com.example.team_project.dto.response.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @POST("orders")
    Call<Void> createOrder(@Body OrderRequest orderRequest);
    @GET("orders/{phone}")
    Call<List<OrderResponse>> getOrders(@Path("phone") String phone);
    @GET("orders/order-items/{orderId}")
    Call<List<OrderResponse>> getOrderItems(@Path("orderId") String phone);
}
