package com.example.team_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team_project.R;
import com.example.team_project.dto.response.OrderResponse;
import com.example.team_project.models.Cart;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<OrderResponse> list;
    private int totalAmount = 0;
    private Long orderId = 0l;

    public OrderAdapter(Context context, List<OrderResponse> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
//        holder.date.setText(list.get(position).getOrderDate().toString());
//        holder.time.setText(list.get(position).getRequiredDate().toString());
        holder.name.setText("Username: "+list.get(position).getName());
        holder.store.setText("Store: "+list.get(position).getStore());
        holder.staff.setText("Staff: "+ list.get(position).getStaff());
        System.out.println("Order status " + list.get(position).getOrderStatus());
        if(list.get(position).getOrderStatus().toString().equals("PENDING")){
            holder.status.setProgress(0);
        }
        if(list.get(position).getOrderStatus().toString().equals("PROCESSING")){
            holder.status.setProgress(1);
        }
        if(list.get(position).getOrderStatus().toString().equals("COMPLETED")){
            holder.status.setProgress(2);
        }
        holder.orderDate.setText("Order: "+list.get(position).getOrderDate());
        holder.requireDate.setText("Require: "+list.get(position).getRequiredDate());
        holder.shippedDate.setText("Position: "+list.get(position).getShippedDate());
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, store,staff,orderDate,requireDate,shippedDate;
        ProgressBar status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            store = itemView.findViewById(R.id.storeTextView);
            staff = itemView.findViewById(R.id.staffTextView);
            orderDate = itemView.findViewById(R.id.orderDateTextView);
            requireDate = itemView.findViewById(R.id.requireDateTextView);
            shippedDate = itemView.findViewById(R.id.shippedDateTextView);
            status = itemView.findViewById(R.id.orderStatusProgressBar);
        }
    }
}
