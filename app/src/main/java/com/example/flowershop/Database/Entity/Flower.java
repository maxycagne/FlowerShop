package com.example.flowershop.Database.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Flower implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String flowerName;

    private double price;

    private int quantity;

    @Ignore
    public Flower() {}


    @Ignore
    public Flower(String flowerName, double price, int quantity) {
        this.flowerName = flowerName;
        this.price = price;
        this.quantity = quantity;
    }

    public Flower(int id, String flowerName, double price, int quantity) {
        this.id = id;
        this.flowerName = flowerName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
