package com.example.flowershop.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.Database.Entity.User;

import java.util.List;

@Dao
public interface FlowerDao {
    @Query("SELECT * FROM flower")
    List<Flower> getAllFlower();

    @Query("UPDATE Flower SET flowerName = :name, quantity = :quantity, price = :price WHERE id = :id")
    void updateFlower(String name,int quantity,double price,int id );

    @Query("SELECT * FROM flower WHERE id = :id")
    Flower findFlowerbyId(int id);


    @Query("SELECT * FROM flower WHERE flowerName = :flowerName")
    List<Flower> getAllByFlowerName(String flowerName);

    @Insert
    void insertAllFlower(Flower flower);

    @Delete
    void deleteFlower(Flower flower);
}
