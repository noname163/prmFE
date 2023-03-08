package com.example.team_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team_project.R;
import com.example.team_project.activity.DetailedActivity;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.Product;

import java.util.List;

public class ProductUserAdapter extends RecyclerView.Adapter<ProductUserAdapter.ViewHolder> {
    private Context context;
    private List<ProductUserResponse> list;

    public ProductUserAdapter(Context context, List<ProductUserResponse> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ProductUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductUserAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductUserAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.newImg);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));
        holder.model.setText(String.valueOf(list.get(position).getModalYear()));
        holder.brand.setText(String.valueOf(list.get(position).getBranch()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newName, newPrice, brand, model, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);
            brand = itemView.findViewById(R.id.product_brand);
            model = itemView.findViewById(R.id.product_model);
        }
    }
}
