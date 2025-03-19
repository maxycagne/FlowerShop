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
import com.example.flowershop.ImageHelper;
import com.example.flowershop.databinding.LstFlowerdetailsBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public Context context;

    public List<Flower> flowerList;

    public FlowerClick flowerClick;

    public ProductAdapter(Context context, List<Flower> flowerList, FlowerClick flowerClick) {
        this.context = context;
        this.flowerList = flowerList;
        this.flowerClick = flowerClick;
    }

    public interface FlowerClick{
        void OnClick(Flower flower);
    }

    public void refreshList(Context context)
    {
        this.flowerList = flowerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LstFlowerdetailsBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Flower flower = flowerList.get(position);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);


        holder.root.flowerImg.setImageDrawable(ImageHelper.getImg(context,flower.getFlowerName()));
        holder.root.txtItemName.setText(flower.getFlowerName());
        holder.root.txtPrice.setText(String.valueOf(flower.getPrice()));
        holder.root.txtQty.setText(String.valueOf(flower.getQuantity()));


        holder.itemView.setOnClickListener(v -> {
            flowerClick.OnClick(flower);
        });



        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LstFlowerdetailsBinding root;
        public ViewHolder(@NonNull LstFlowerdetailsBinding root) {
            super(root.getRoot());
            this.root = root;
        }
    }
}
