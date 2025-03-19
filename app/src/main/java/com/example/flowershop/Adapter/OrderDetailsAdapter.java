package com.example.flowershop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.databinding.LstOrderdetailsBinding;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    public Context context;

    public List<OrderDetails> orderDetailsList;

    public OrderDetailsClick orderDetailsClick;

    public OrderDetailsAdapter(Context context, List<OrderDetails> orderDetailsList, OrderDetailsClick orderDetailsClick) {
        this.context = context;
        this.orderDetailsList = orderDetailsList;
        this.orderDetailsClick = orderDetailsClick;
    }

    public interface OrderDetailsClick{
        void Onclick(OrderDetails orderDetails);
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LstOrderdetailsBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {
        OrderDetails orderDetails = orderDetailsList.get(position);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

        holder.root.txtCustomerName.setText(orderDetails.getName());
        holder.root.txtOrderDate.setText(orderDetails.getDate());
        holder.root.txtStatus.setText(orderDetails.getStatus());
        holder.root.txtTotalDue.setText(String.valueOf(orderDetails.getTotalAmount()));

        holder.itemView.setOnClickListener(v->{
            orderDetailsClick.Onclick(orderDetails);
        });

        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LstOrderdetailsBinding root;
        public ViewHolder(@NonNull LstOrderdetailsBinding root) {
            super(root.getRoot());
            this.root = root;
        }
    }
}
