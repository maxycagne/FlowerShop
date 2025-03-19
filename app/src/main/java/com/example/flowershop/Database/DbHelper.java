package com.example.flowershop.Database;

import android.content.Context;

import com.example.flowershop.Database.Dao.CartDao;
import com.example.flowershop.Database.Dao.FlowerDao;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.Database.Dao.UserDao;

public class DbHelper {

    public RoomDb roomDb;


    public FlowerDao flowerDao;
    public UserDao userDao;

    public CartDao cartDao;

    public OrderDetailsDao orderDetailsDao;

    public DbHelper(Context context) {
        this.roomDb = RoomDb.getINSTANCE(context);
        this.userDao = roomDb.userDao();
        this.flowerDao = roomDb.flowerDao();
        this.orderDetailsDao = roomDb.orderDetailsDao();
        this.cartDao = roomDb.cartDao();
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public RoomDb getRoomDb() {
        return roomDb;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public FlowerDao getFlowerDao() {
        return flowerDao;
    }

    public OrderDetailsDao getOrderDetailsDao() {
        return orderDetailsDao;
    }
}
