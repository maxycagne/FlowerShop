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

import com.example.flowershop.Adapter.FlowerCartList;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityCartBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding root;


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
        root = ActivityCartBinding.inflate(getLayoutInflater());
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

        root.btnCheckOut.setOnClickListener(v->{
            startActivity(new Intent(this, Billing.class));
            finish();
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

            double finalTotalPrice = totalPrice;

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

                root.lstCart.setAdapter(adapter);
                root.edtTotalPrice.setText("P " + finalTotalPrice);
                
            });
        });
    }

    public void deleteCart(Cart cart)
    {
        Executors.newSingleThreadExecutor().submit(()->{
            dbHelper.getCartDao().deleteCartItem(cart);

            handler.post(()->{
                Toast.makeText(this, "Cart is deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        retrieve();
    }

}