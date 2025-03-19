package com.example.flowershop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.ImageHelper;
import com.example.flowershop.databinding.LstCartItemsBinding;
import com.example.flowershop.databinding.LstCustomerHpgCardsBinding;
import com.example.flowershop.databinding.LstFlowerdetailsBinding;

import java.util.List;

public class FlowerCartList extends RecyclerView.Adapter<FlowerCartList.ViewHolder> {
    public Context context;

    public List<Cart> cartList;

    public CartClick cartClick;

    public FlowerCartList(Context context, List<Cart> cartList, CartClick cartClick) {
        this.context = context;
        this.cartList = cartList;
        this.cartClick = cartClick;
    }

    public interface CartClick{
        void OnClick(Cart cart);

        void OnDelete(Cart cart);

    }

    public void refreshList(Context context)
    {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerCartList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FlowerCartList.ViewHolder(LstCartItemsBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerCartList.ViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);


        holder.root.flowerImg.setImageDrawable(ImageHelper.getImg(context,cart.getFlowerName()));
        holder.root.txtItemName.setText(cart.getFlowerName());
        holder.root.txtPrice.setText(String.valueOf(cart.getPrice()));


        holder.root.btnDelete.setOnClickListener(v -> {
            cartClick.OnDelete(cart);
        });



        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LstCartItemsBinding root;
        public ViewHolder(@NonNull LstCartItemsBinding root) {
            super(root.getRoot());
            this.root = root;
        }
    }
}