package com.example.flowershop.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.flowershop.Database.Dao.CartDao;
import com.example.flowershop.Database.Dao.FlowerDao;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.Database.Dao.UserDao;
import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.Database.Entity.User;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Flower.class, OrderDetails.class, Cart.class}, version = 1)
public abstract class RoomDb extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract CartDao cartDao();

    public abstract FlowerDao flowerDao();

    public abstract OrderDetailsDao orderDetailsDao();

    private static RoomDb INSTANCE;

    public synchronized static RoomDb getINSTANCE(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context,RoomDb.class,"FlowerShop.db").addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    UserDao userDao = RoomDb.INSTANCE.userDao();
                    FlowerDao flowerDao = RoomDb.INSTANCE.flowerDao();

                    Executors.newSingleThreadExecutor().submit(()->{

                        userDao.insertAllUser(new User("q","Maverick","C","Cagne","+639318992301","q","Tarlac City","Vendor"));


                        flowerDao.insertAllFlower(new Flower("Daisy",50.0,50));
                        flowerDao.insertAllFlower(new Flower("Rose",100.0,50));
                        flowerDao.insertAllFlower(new Flower("Sunflower",150.0,50));
                        flowerDao.insertAllFlower(new Flower("Tulip",180.0,50));
                        flowerDao.insertAllFlower(new Flower("Peony",200.0,50));


                    });
                }
            }).build();
        }
        return INSTANCE;
    }
}