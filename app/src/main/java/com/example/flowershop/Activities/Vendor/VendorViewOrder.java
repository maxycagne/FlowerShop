package com.example.flowershop.Activities.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityVendorViewOrderBinding;

import java.util.concurrent.Executors;

public class VendorViewOrder extends AppCompatActivity {

    private ActivityVendorViewOrderBinding root;

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
        root = ActivityVendorViewOrderBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();


        retriveOrder();

        root.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this,VendorHome.class));
        });

        root.btnCancelOrder.setOnClickListener(v -> {

        });


    }

    public void retriveOrder()
    {
        Executors.newSingleThreadExecutor().submit(()->{
           dbHelper.orderDetailsDao.getAllOrderDetails();
        });

    }

    public void onResume()
    {
        super.onResume();

    }
}