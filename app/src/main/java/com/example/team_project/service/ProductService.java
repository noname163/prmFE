package com.example.team_project.service;

import com.example.team_project.dto.request.PageableRequest;
import com.example.team_project.dto.response.PaginationResponse;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductService {
    @POST("products")
    Call<PaginationResponse<List<ProductUserResponse>>> getProducts(@Body PageableRequest pageable);
    @POST("products/by-category/{id}")
    Call<PaginationResponse<List<ProductUserResponse>>> getProductsByCategory(@Path("id") Long id, @Body PageableRequest pageable);
}

