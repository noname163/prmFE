package com.example.team_project.adapter;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team_project.R;
import com.example.team_project.activity.CartActivity;
import com.example.team_project.models.Cart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private Context context;
    private List<Cart> list;
    private int totalAmount = 0;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public CartAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView name, price, date, time, totalQuantity, totalPrice;
        ImageView img_product;
        ImageView deleteBtn, subtratBtn, plushBtn;
        String cartId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            auth = FirebaseAuth.getInstance();
            firestore = FirebaseFirestore.getInstance();
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.total_price);
            img_product = itemView.findViewById(R.id.img_product);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteBtn = itemView.findViewById(R.id.delete);
            subtratBtn = itemView.findViewById(R.id.subtract);
            plushBtn = itemView.findViewById(R.id.plush);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Cart id " + cartId);
                    Intent intent = new Intent(context, CartActivity.class);
                    intent.putExtra("cartId", cartId);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Cart cart) {
            name.setText(cart.getProductName());
            price.setText(cart.getProductPrice()+"$");
            totalQuantity.setText(cart.getTotalQuantity());
            totalPrice.setText(cart.getTotalPrice()+"$");
            Glide.with(context).load(cart.getProductImage()).into(img_product);
            cartId = cart.getCartId();
        }
    }
}

