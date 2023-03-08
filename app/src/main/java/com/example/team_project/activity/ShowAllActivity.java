package com.example.team_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.team_project.R;
import com.example.team_project.adapter.ProductUserResponseAdapter;
import com.example.team_project.adapter.ShowAllAdapter;
import com.example.team_project.dto.request.PageableRequest;
import com.example.team_project.dto.response.PaginationResponse;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.ShowAll;
import com.example.team_project.retrofitclient.RetrofitClient;
import com.example.team_project.service.ProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowAllActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
    ProductUserResponseAdapter productUserResponseAdapter;
    List<ShowAll> showAllList;
    FirebaseFirestore firestore;
    ProductService productService;
    PageableRequest pageable = new PageableRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("On create");
        Retrofit retrofitClient = RetrofitClient.getClient();
        productService = retrofitClient.create(ProductService.class);
        setContentView(R.layout.layout_show_all);

        //type
        String type = getIntent().getStringExtra("type");
        Long id = getIntent().getLongExtra("id",0l);
        System.out.println("Type "+type);
        System.out.println("Id "+id);
        //toolbar
        toolbar = findViewById(R.id.toolbarShowAll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        List<ProductUserResponse> productList = new ArrayList<>();
        productUserResponseAdapter = new ProductUserResponseAdapter(this, productList);
        recyclerView.setAdapter(productUserResponseAdapter);
        pageable.setField("name");
        pageable.setSize(5);
        pageable.setSortType("ASC");
        pageable.setOffSet(0);
        if(id == null || id ==0l){
            System.out.println("Type "+ type);
            System.out.println("Load data...");
            Call<PaginationResponse<List<ProductUserResponse>>> call = productService.getProducts(pageable);
            call.enqueue(new Callback<PaginationResponse<List<ProductUserResponse>>>() {
                @Override
                public void onResponse(Call<PaginationResponse<List<ProductUserResponse>>> call, Response<PaginationResponse<List<ProductUserResponse>>> response) {
                    System.out.println("Load data...");

                    if (response.isSuccessful()) {
                        System.out.println("Success...");
                        PaginationResponse<List<ProductUserResponse>> paginationResponse = response.body();
                        List<ProductUserResponse> products = paginationResponse.getData();
                        for (ProductUserResponse product: products
                             ) {
                            System.out.println("Product " + product.toString());
                            productList.add(product);
                            productUserResponseAdapter.notifyDataSetChanged();
                            System.out.println("Load data complted");
                        }
                    } else {
                        System.out.println("Load data fail...");
                    }
                }

                @Override
                public void onFailure(Call<PaginationResponse<List<ProductUserResponse>>> call, Throwable t) {
                    System.out.println("Load data fail..." + t.getMessage());
                }
            });
        }

        if (id != null && id != 0l){
            System.out.println("Load data by category");
            System.out.println("Category id "+id);
            Call<PaginationResponse<List<ProductUserResponse>>> call = productService.getProductsByCategory(id,pageable);
            call.enqueue(new Callback<PaginationResponse<List<ProductUserResponse>>>() {
                             @Override
                             public void onResponse(Call<PaginationResponse<List<ProductUserResponse>>> call, Response<PaginationResponse<List<ProductUserResponse>>> response) {
                                 System.out.println("Load data...");
                                 if (response.isSuccessful()) {
                                     System.out.println("Success...");
                                     PaginationResponse<List<ProductUserResponse>> paginationResponse = response.body();
                                     List<ProductUserResponse> products = paginationResponse.getData();
                                     for (ProductUserResponse product: products
                                     ) {
                                         System.out.println("Product " + product.toString());
                                         productList.add(product);
                                         productUserResponseAdapter.notifyDataSetChanged();
                                         System.out.println("Load data complted");
                                     }
                                 } else {
                                     System.out.println("Load data fail...");
                                 }
                             }
                             @Override
                             public void onFailure(Call<PaginationResponse<List<ProductUserResponse>>> call, Throwable t) {
                                 System.out.println("Load data fail..." + t.getMessage());
                             }
        });
    }
}
}