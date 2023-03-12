package com.example.team_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team_project.R;
import com.example.team_project.dto.request.OrderItemRequest;
import com.example.team_project.dto.request.OrderItemRequests;
import com.example.team_project.dto.request.OrderRequest;
import com.example.team_project.models.Cart;
import com.example.team_project.retrofitclient.RetrofitClient;
import com.example.team_project.service.OrderService;
import com.example.team_project.service.ProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmOrderActivity extends AppCompatActivity {
    private Button confirmButton;
    private OrderService orderService;
    private OrderRequest orderRequest = new OrderRequest();
    private  String  phonenumber = "";
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_layout);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        Retrofit retrofitClient = RetrofitClient.getClient();
        orderService = retrofitClient.create(OrderService.class);

        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        confirmButton = findViewById(R.id.confirm_button);
        EditText phonenumberEditText = findViewById(R.id.customer_phone);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        String cartId = doc.getId();
                        OrderItemRequest orderItemRequest = new OrderItemRequest();
                        Cart cart = doc.toObject(Cart.class);
                        orderItemRequest.setProductId(cart.getProductId());
                        orderItemRequest.setQuantity(Integer.parseInt(cart.getTotalQuantity()));
                        orderItemRequests.add(orderItemRequest);
                    }
                    for (OrderItemRequest orderItem: orderItemRequests
                    ) {
                        System.out.println("Order Item in Confirm " + orderItem.toString());
                    }
                }
            }

        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phonenumberEditText.getText().equals("")){
                    Toast.makeText(ConfirmOrderActivity.this, "Phone number cannot null", Toast.LENGTH_SHORT).show();
                }
                phonenumber =  phonenumberEditText.getText().toString();
                System.out.println("Phone " + phonenumber);
                orderRequest.setOrderItems(orderItemRequests);
                orderRequest.setPhone(phonenumber);
                Call<Void> call = orderService.createOrder(orderRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("Create order....");
                        if(response.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),OrderActivity.class );
                            System.out.println("Create success");
                            intent.putExtra("CustomerPhone", phonenumber);
                            CollectionReference cartItemsRef = firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User");
                            cartItemsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    WriteBatch batch = firestore.batch();
                                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                        batch.delete(document.getReference());
                                    }
                                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                System.out.println("Remove Cart");
                                                startActivity(intent);
                                            } else {
                                                // Handle error
                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Errors " + t.getMessage());
                    }
                });
            }
        });
    }
}
