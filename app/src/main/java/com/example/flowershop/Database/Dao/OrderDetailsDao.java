package com.example.flowershop.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.Database.Entity.User;

import java.util.List;

@Dao
public interface OrderDetailsDao {

    @Query("SELECT * FROM orderDetails")
    List<OrderDetails> getAllOrderDetails();


    @Query("SELECT * FROM orderDetails WHERE id LIKE :id LIMIT 1")
    OrderDetails getAllOrderDetailsByid(int id);




    @Insert
    void insertAllOrderDetails(OrderDetails orderDetails);

    @Delete
    void deleteOrderDetails(OrderDetails orderDetails);
}
