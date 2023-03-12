package com.example.team_project.activity;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team_project.R;
import com.example.team_project.adapter.CartAdapter;
import com.example.team_project.dto.request.OrderItemRequest;
import com.example.team_project.dto.request.OrderItemRequests;
import com.example.team_project.models.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Cart> cartList;
    CartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    TextView totalAmount;
    Button buyBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);
        LayoutInflater inflater = LayoutInflater.from(this);
        View cartItemView = inflater.inflate(R.layout.my_cart_item, null);
        String cartId = getIntent().getStringExtra("cartId");
        System.out.println("CartId " + cartId);

        //firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

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

        //get data from my cart adapter
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));
        buyBtn = findViewById(R.id.buyBtn);
        totalAmount = findViewById(R.id.textView3);
        recyclerView = findViewById(R.id.order_history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartList);
        recyclerView.setAdapter(cartAdapter);


        if(cartId!=null&&cartId.length()>0){
            System.out.println("Pending remove item " + cartId);
            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                    .collection("User")
                    .document(cartId)
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("Remove sucess");
                                Toast.makeText(CartActivity.this, "Removed From Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CartActivity.this, "Removed From Cart Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<OrderItemRequest> orderItemRequests =  new ArrayList<>();
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        String cartId = doc.getId();
                        Cart cart = doc.toObject(Cart.class);
                        cart.setCartId(cartId);
                        cartList.add(cart);
                        cartAdapter.notifyDataSetChanged();
                    }
                    for (OrderItemRequest orderItem: orderItemRequests
                         ) {
                        System.out.println("Order Item " + orderItem.toString());
                    }
                }
            }
            
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,ConfirmOrderActivity.class));
                finish();
            }
        });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount",0);
            totalAmount.setText("Total Amount: "+totalBill+"$");
        }
    };
}