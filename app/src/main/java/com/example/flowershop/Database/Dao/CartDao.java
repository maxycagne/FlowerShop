package com.example.flowershop.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.Database.Entity.Flower;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insertAllCart(Cart carts);

    @Query("SELECT * FROM Cart")
    List<Cart> getAllCartItems();

    @Query("DELETE FROM cart")
    void clearCart();



    @Update
    void updateCart(Cart cart);

    @Delete
    void deleteCartItem(Cart cart);
}
