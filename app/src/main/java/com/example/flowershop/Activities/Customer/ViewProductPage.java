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

import com.example.flowershop.Activities.Customer.Fragments.CustomerHomepage;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.ImageHelper;
import com.example.flowershop.R;
import com.example.flowershop.Database.Entity.Cart;
import com.example.flowershop.databinding.ActivityViewProductPageBinding;

import java.util.concurrent.Executors;

public class ViewProductPage extends AppCompatActivity {

    private ActivityViewProductPageBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    String name;

    Double price;

    int quantity;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityViewProductPageBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

         name = getIntent().getStringExtra("name");
         price = getIntent().getDoubleExtra("price",0.0);

        root.txtProductName.setText(name);
        root.txtPrice.setText(String.valueOf(price));
        root.imgProductPhoto.setImageDrawable(ImageHelper.getImg(this,name));


        root.btnBuynow.setOnClickListener(v->{
            if(validate())
            {
                return;
            }
            addCart();
            startActivity(new Intent(this, Billing.class));
            finish();
        });

        root.btnBack.setOnClickListener(v->{
            startActivity(new Intent(this, CustomerHome.class));
            finish();
        });

        root.btnAddToCart.setOnClickListener(v -> {
            if(validate())
            {
                return;
            }
            addCart();
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });

    }

    public void addCart() {


        int quantitys = Integer.parseInt(root.edtQty.getText().toString());
        Executors.newSingleThreadExecutor().submit(() -> {
            Cart cart = new Cart(name, price, quantitys);
            dbHelper.getCartDao().insertAllCart(cart);

            handler.post(() -> {

                Toast.makeText(this, "Product added to the cart", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public boolean validate()
    {
        boolean validate = true;

        if(root.edtQty.getText().toString().equals(""))
        {
            root.layoutEdtQty.setError("Input Quantity");
            root.layoutEdtQty.setErrorEnabled(true);
            validate = true;
        }
        else
        {
            root.layoutEdtQty.setError("");
            root.layoutEdtQty.setErrorEnabled(false);
            validate = false;
        }


        return validate;
    }
}