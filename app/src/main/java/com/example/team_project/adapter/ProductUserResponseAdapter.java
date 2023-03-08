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

import java.util.List;

public class ProductUserResponseAdapter extends RecyclerView.Adapter<ProductUserResponseAdapter.ViewHolder> {

    private Context context;
    private List<ProductUserResponse> list;

    public ProductUserResponseAdapter(Context context, List<ProductUserResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductUserResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");
        return new ProductUserResponseAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ProductUserResponseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.mItemImage);
        holder.mCost.setText("$"+String.valueOf(list.get(position).getPrice()));
        holder.mName.setText(list.get(position).getName());
        System.out.println("onBindViewHolder");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", list.get(position));
                context.startActivity(intent);
            }
        });
        System.out.println("onBindViewHolder success");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mItemImage;
        private TextView mCost;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_image);
            mCost = itemView.findViewById(R.id.item_cost);
            mName = itemView.findViewById(R.id.item_nam);
        }
    }
}
