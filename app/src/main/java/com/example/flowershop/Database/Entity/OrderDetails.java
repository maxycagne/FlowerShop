package com.example.flowershop.Database.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class OrderDetails implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;

    private String flowerName;


    private int quantity;

    private String contactNum;

    private String address;

    private String date;

    private double totalAmount;

    private String status;

    @Ignore
    public OrderDetails() {
    }

    @Ignore
    public OrderDetails(String name, String flowerName, int quantity, String contactNum, String address, String date, double totalAmount, String status) {
        this.name = name;
        this.flowerName = flowerName;
        this.quantity = quantity;
        this.contactNum = contactNum;
        this.address = address;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public OrderDetails(int id, String name, String flowerName, int quantity, String contactNum, String address, String date, double totalAmount, String status) {
        this.id = id;
        this.name = name;
        this.flowerName = flowerName;
        this.quantity = quantity;
        this.contactNum = contactNum;
        this.address = address;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
