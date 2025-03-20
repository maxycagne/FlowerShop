package com.example.flowershop.Activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowershop.Database.Entity.Cart;

import com.example.flowershop.Adapter.FlowerCartList;

import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityBillingBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class Billing extends AppCompatActivity {
    private ActivityBillingBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityBillingBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        retrieve();

        root.btnBack.setOnClickListener(v->{
            startActivity(new Intent(this, CustomerHome.class));
            finish();
        });

        root.btnCheckout.setOnClickListener(v->{

            addOrder();
        });

    }

    public void retrieve()
    {
        Executors.newSingleThreadExecutor().submit(()->{
            List<Cart> cartList = dbHelper.cartDao.getAllCartItems();

            double totalPrice = 0;
            for(Cart cart : cartList)
            {
                totalPrice += cart.getPrice() * cart.getQuantity();
            }

            double finaltotal = totalPrice;

            handler.post(()->{
                FlowerCartList adapter = new FlowerCartList(this,cartList,new FlowerCartList.CartClick() {

                    @Override
                    public void OnClick(Cart cart) {

                    }

                    @Override
                    public void OnDelete(Cart cart) {
                        deleteCart(cart);
                        retrieve();
                    }
                });

                root.lstOrderItems.setAdapter(adapter);
                root.txtTotalPrice.setText("P "+finaltotal);


            });
        });
    }

    public void deleteCart(Cart cart)
    {
        Executors.newSingleThreadExecutor().submit(()->{
            dbHelper.getCartDao().deleteCartItem(cart);

            handler.post(()->{
                Toast.makeText(Billing.this, "Cart is deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }




    public void addOrder() {
        if (!validate()) {
            return;
        }


        Executors.newSingleThreadExecutor().submit(() -> {
            List<Cart> cartList = dbHelper.cartDao.getAllCartItems();
            if (cartList.isEmpty()) {
                handler.post(() -> Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show());
                return;
            }

            for (Cart cart : cartList) {
                Flower flower = dbHelper.flowerDao.findFlowerbyFlowerName(cart.getFlowerName());
                if (flower != null && flower.getQuantity() < cart.getQuantity()) {
                    handler.post(() -> Toast.makeText(this, "Not enough stock for " + flower.getFlowerName(), Toast.LENGTH_SHORT).show());
                    return;
                }
            }

            double totalAmount = 0;
            StringBuilder flowerNames = new StringBuilder();
            for (Cart cart : cartList) {
                Flower flower = dbHelper.flowerDao.findFlowerbyFlowerName(cart.getFlowerName());
                if (flower != null) {
                    flower.setQuantity(flower.getQuantity() - cart.getQuantity());
                    dbHelper.flowerDao.updateFlower(flower);
                }

                totalAmount += cart.getPrice() * cart.getQuantity();
                flowerNames.append(cart.getFlowerName()).append(", ");
            }




            String name = root.edtFirstName.getText().toString();
            String contactNum = root.edtContactNumber.getText().toString();
            String address = root.edtAddress.getText().toString();
            String date = "2025-20-03";
            String status = "Pending";



            OrderDetails order = new OrderDetails();
            order.setName(name);
            order.setFlowerName(flowerNames.toString());
            order.setQuantity(cartList.size());
            order.setContactNum(contactNum);
            order.setAddress(address);
            order.setDate(date);
            order.setTotalAmount(totalAmount);
            order.setStatus(status);

            dbHelper.orderDetailsDao.insertAllOrderDetails(order);
            dbHelper.cartDao.clearCart();


            handler.post(() -> {
                Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CustomerHome.class));
                finish();
            });
        });
    }


    public boolean validate()
    {
        boolean validate = true;
        if(root.edtFirstName.getText().toString().equals(""))
        {
            root.layoutEdtCustomerName.setError("Input Name");
            root.layoutEdtCustomerName.setErrorEnabled(true);
            validate = false;
        }
        else
        {
            root.layoutEdtCustomerName.setError("");
            root.layoutEdtCustomerName.setErrorEnabled(false);

        }

        if(root.edtAddress.getText().toString().equals(""))
        {
            root.layoutEdtAddress.setError("Input Address");
            root.layoutEdtAddress.setErrorEnabled(true);
            validate = false;
        }
        else
        {
            root.layoutEdtAddress.setError("");
            root.layoutEdtAddress.setErrorEnabled(false);

        }

        if(root.edtContactNumber.getText().toString().equals(""))
        {
            root.layoutEdtContactNumber.setError("Input Contact Number");
            root.layoutEdtContactNumber.setErrorEnabled(true);
            validate = false;
        }
        else
        {
            root.layoutEdtContactNumber.setError("");
            root.layoutEdtContactNumber.setErrorEnabled(false);

        }

        return validate;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        retrieve();

    }
}