package com.example.team_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.team_project.R;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.PopularProducts;
import com.example.team_project.models.Product;
import com.example.team_project.models.ShowAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg;
    TextView rating,name,description,price,quantity, model;
    Button addToCart,buyNow;
    ImageView addItems,removeItems,productImage;
    Toolbar toolbar;
    int totalQuantity=1;
    int totalPrice=0;
    //Product
    Product product = null;
    //Popular
    PopularProducts popularProducts = null;
    //Show All
    ShowAll showAll = null;

    ProductUserResponse productUserResponse = null;

    //Firebase
    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detailed);
        //firestore
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof Product){
            product = (Product) obj;
        } else if (obj instanceof  PopularProducts) {
            popularProducts = (PopularProducts) obj;
        }else if (obj instanceof  ShowAll) {
            showAll = (ShowAll) obj;
        }else if(obj instanceof ProductUserResponse){
            productUserResponse = (ProductUserResponse) obj;
        }
        //toolbar
        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TextView
        detailedImg = findViewById(R.id.detailed_img);
        detailedImg.setTag("");
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        model = findViewById(R.id.product_model);



        //Button
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        //Image View
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
//        productImage = findViewById(R.id.detailed_img)
        //Product
        if(product!=null){
            Glide.with(getApplicationContext()).load(product.getImg_url()).into(detailedImg);
            detailedImg.setTag(product.getImg_url());
            name.setText(product.getName());
            rating.setText(product.getRating());
            description.setText(product.getDescription());
            price.setText(String.valueOf(product.getPrice()));
            totalPrice=product.getPrice()*totalQuantity;
        }

        if(productUserResponse!=null){
            Glide.with(getApplicationContext()).load(productUserResponse.getImageUrl()).into(detailedImg);
            detailedImg.setTag(productUserResponse.getImageUrl());
            name.setText(productUserResponse.getName());
//            rating.setText(product.getRating());
//            description.setText(product.getDescription());
            price.setText(String.valueOf(productUserResponse.getPrice()));
            totalPrice=productUserResponse.getPrice().intValue()*totalQuantity;
        }

        if(popularProducts!=null){
            Glide.with(getApplicationContext()).load(popularProducts.getImg_url()).into(detailedImg);
            detailedImg.setTag(popularProducts.getImg_url());
            name.setText(popularProducts.getName());
            rating.setText(popularProducts.getRating());
            description.setText(popularProducts.getDescription());
            price.setText(String.valueOf(popularProducts.getPrice()));
            totalPrice=popularProducts.getPrice()*totalQuantity;
        }

        if(showAll!=null){
            Glide.with(getApplicationContext()).load(showAll.getImg_url()).into(detailedImg);
            detailedImg.setTag(showAll.getImg_url());
            name.setText(showAll.getName());
            rating.setText(showAll.getRating());
            description.setText(showAll.getDescription());
            price.setText(String.valueOf(showAll.getPrice()));
            totalPrice=showAll.getPrice()*totalQuantity;
        }

        if(productUserResponse!=null){
            Glide.with(getApplicationContext()).load(productUserResponse.getImageUrl()).into(detailedImg);
            detailedImg.setTag(productUserResponse.getImageUrl());
            name.setText(productUserResponse.getName());
            price.setText(String.valueOf(productUserResponse.getPrice()));
            model.setText(String.valueOf(productUserResponse.getModalYear()));
            description.setText(String.valueOf(productUserResponse.getDescription()));
            totalPrice=productUserResponse.getPrice().intValue()*totalQuantity;
        }

        //Buy Now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this,AddressActivity.class);
                if(product!=null){
                    intent.putExtra("item",product);
                }

                if(popularProducts!=null){
                    intent.putExtra("item",popularProducts);
                }
                startActivity(intent);
            }
        });
        //Add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity< 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(product!=null){
                        totalPrice=product.getPrice()*totalQuantity;
                    }
                    if(popularProducts!=null){
                        totalPrice=popularProducts.getPrice()*totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }
    private void addtoCart(){
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("productImage",detailedImg.getTag().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}