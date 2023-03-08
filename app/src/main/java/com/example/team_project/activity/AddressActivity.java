package com.example.team_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.team_project.R;
import com.example.team_project.adapter.AddressAdapter;
import com.example.team_project.models.Address;
import com.example.team_project.models.Cart;
import com.example.team_project.models.PopularProducts;
import com.example.team_project.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    Button addAddress;
    RecyclerView recyclerView;
    private List<Address> addressList;
    FirebaseFirestore firestore;
    AddressAdapter addressAdapter;
    FirebaseAuth auth;
    Button addAddressBtn,paymentBtn;
    Toolbar toolbar;
    String mAddress = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address);


        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get Data from detailed activity
        Object obj= getIntent().getSerializableExtra("item");

        //Firebase
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        addAddress = findViewById(R.id.add_address_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),addressList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        Address address = doc.toObject(Address.class);
                        addressList.add(address);
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               double amount = 0.0;
               if(obj instanceof Product){
                   Product product = (Product) obj;
                   amount = product.getPrice();
               }if(obj instanceof PopularProducts){
                   PopularProducts popularProducts= (PopularProducts) obj;
                   amount = popularProducts.getPrice();
                }
               Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
               intent.putExtra("amount",amount);
               startActivity(intent);
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}