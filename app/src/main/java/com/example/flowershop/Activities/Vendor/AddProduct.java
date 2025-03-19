package com.example.flowershop.Activities.Vendor;

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

import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityAddProductBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class AddProduct extends AppCompatActivity {

    private ActivityAddProductBinding root;

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
        root = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        root.btnAdd.setOnClickListener(v -> {
            addProduct();
        });
    }

    private void addProduct()
    {
        validate();
        String productName = root.edtProductName.getText().toString();
        int quantity = Integer.parseInt(root.edtQuantity.getText().toString());
        double price = Double.parseDouble(root.edtPrice.getText().toString());
        Executors.newSingleThreadExecutor().submit(()->
        {
            List<Flower> flowerList = dbHelper.flowerDao.getAllFlower();
            if(flowerList != null)
            {
                Toast.makeText(this, "flower already at the shop", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Flower flower = new Flower(productName,price,quantity);
                dbHelper.flowerDao.insertAllFlower(flower);

                handler.post(()->{
                    Toast.makeText(this, "flower successfully added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,VendorHome.class));
                });

            }
        });
    }

    private void validate()
    {
        if(root.edtProductName.getText().toString().equals(""))
        {
            root.layoutEdtProductName.setError("Fill up product name");
            root.layoutEdtProductName.setErrorEnabled(true);
        }
        else {
            root.layoutEdtProductName.setError("");
            root.layoutEdtProductName.setErrorEnabled(false);
        }

        if(root.edtPrice.getText().toString().equals(""))
        {
            root.layoutEdtPrice.setError("Fill up edtPrice");
            root.layoutEdtPrice.setErrorEnabled(true);
        }
        else {
            root.layoutEdtPrice.setError("");
            root.layoutEdtPrice.setErrorEnabled(false);
        }

        if(root.edtQuantity.getText().toString().equals(""))
        {
            root.layoutEdtQuantity.setError("Fill up quantity");
            root.layoutEdtQuantity.setErrorEnabled(true);
        }
        else {
            root.layoutEdtPrice.setError("");
            root.layoutEdtQuantity.setErrorEnabled(false);
        }
    }
}