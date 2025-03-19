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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flowershop.Activities.Customer.Fragments.CustomerHomepage;
import com.example.flowershop.Activities.Customer.Fragments.MyOrders;
import com.example.flowershop.Activities.Vendor.Fragments.HomeFragment;
import com.example.flowershop.Activities.Vendor.Fragments.ProductFragment;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityCustomerHomeBinding;
import com.example.flowershop.databinding.ActivityVendorHomeBinding;

public class CustomerHome extends AppCompatActivity {

    private ActivityCustomerHomeBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(this);
    }

    public Handler getHandler() {
        return handler;
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        root = ActivityCustomerHomeBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());

        setDb();

        root.btnCart.setOnClickListener(v->{
            startActivity(new Intent(this, CartActivity.class));
            finish();
        });

        ReplaceFragment(new CustomerHomepage());

        root.customerBottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.customerHomepage)
            {
                ReplaceFragment(new CustomerHomepage());
            }
            else if (item.getItemId() == R.id.myOrders) {
                ReplaceFragment(new MyOrders());
            }

            return true;
        });

    }

    public void ReplaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFramelayout,fragment);
        fragmentTransaction.commit();
    }
}