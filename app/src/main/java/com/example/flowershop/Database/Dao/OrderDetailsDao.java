package com.example.flowershop.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.Database.Entity.User;

import java.util.List;

@Dao
public interface OrderDetailsDao {

    @Query("SELECT * FROM orderDetails")
    List<OrderDetails> getAllOrderDetails();


    @Query("SELECT * FROM orderDetails WHERE id LIKE :id LIMIT 1")
    OrderDetails getAllOrderDetailsByid(int id);


    @Query("UPDATE OrderDetails SET status = :status WHERE id = :id")
    void updateOrder(String status,int id );

    @Insert
    void insertAllOrderDetails(OrderDetails orderDetails);

    @Query("SELECT * FROM orderdetails WHERE status = :status")
    List<OrderDetails> getAllByOrderDetailsStatus(String status);

    @Delete
    void deleteOrderDetails(OrderDetails orderDetails);
}
