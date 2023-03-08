package com.example.team_project.service;

import com.example.team_project.dto.request.PageableRequest;
import com.example.team_project.dto.response.PaginationResponse;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryService {
    @GET("categories")
    Call<List<Category>> getCategories();
}
