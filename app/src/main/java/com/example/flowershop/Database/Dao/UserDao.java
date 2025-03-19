package com.example.flowershop.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.flowershop.Database.Entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    User getAllUser(String username, String password);


    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User getAllUserByName(String username);


    @Insert
    void insertAllUser(User users);

    @Delete
    void deleteUser(User user);
}
