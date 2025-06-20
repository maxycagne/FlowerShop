package com.example.flowershop.Activities.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowershop.Activities.All.Login;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityCustomerProfileBinding;
import com.example.flowershop.databinding.ActivityVendorViewOrderBinding;

public class CustomerProfile extends AppCompatActivity {
    private ActivityCustomerProfileBinding root;


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
        root = ActivityCustomerProfileBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        root.btnBack.setOnClickListener(v->{
            startActivity(new Intent(this, CustomerHome.class));
            finish();
        });

        root.btnLogout.setOnClickListener(v->{
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }
}