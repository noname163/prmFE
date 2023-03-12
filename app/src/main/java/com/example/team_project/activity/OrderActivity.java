package com.example.team_project.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project.R;
import com.example.team_project.adapter.CartAdapter;
import com.example.team_project.adapter.OrderAdapter;
import com.example.team_project.dto.request.OrderItemRequest;
import com.example.team_project.dto.response.OrderResponse;
import com.example.team_project.models.Cart;
import com.example.team_project.retrofitclient.RetrofitClient;
import com.example.team_project.service.OrderService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private CartAdapter cartAdapter;
    private  List<OrderResponse> orderResponses;
    private OrderAdapter orderAdapter;
    private OrderService orderService;
    private String phone = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_history);

        //toolbar
        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent getIntent = getIntent();
        phone = getIntent.getStringExtra("CustomerPhone");
        recyclerView = findViewById(R.id.order_history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartList);
        orderResponses = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderResponses);
        recyclerView.setAdapter(orderAdapter);

        Retrofit retrofitClient = RetrofitClient.getClient();
        orderService = retrofitClient.create(OrderService.class);
        System.out.println("Customer phone " + phone);
        Call<List<OrderResponse>> call = orderService.getOrders(phone);
        call.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                System.out.println("Perform get order");
                if (response.isSuccessful()){
                    for (OrderResponse orderResponse: response.body()
                         ) {
                            orderResponses.add(orderResponse);
                        System.out.println("Order Response " + orderResponse.toString());
                            orderAdapter.notifyDataSetChanged();

                    }
                }
                else{
                    Toast.makeText(OrderActivity.this, "Cannot Load Order From Serve", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                System.out.println("Error " + t.getMessage());
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
