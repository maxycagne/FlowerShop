package com.example.flowershop.Activities.Vendor;

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

import com.example.flowershop.Activities.Vendor.Fragments.HomeFragment;
import com.example.flowershop.Activities.Vendor.Fragments.ProductFragment;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityVendorHomeBinding;

public class VendorHome extends AppCompatActivity {

    private ActivityVendorHomeBinding root;

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
        root = ActivityVendorHomeBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        setDb();

        ReplaceFragment(new HomeFragment());

        root.vendorBottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.homeFragment)
            {
                ReplaceFragment(new HomeFragment());
                root.txtHeader.setText("Home");


            }
            else if (item.getItemId() == R.id.productFragment) {
                ReplaceFragment(new ProductFragment());
                root.txtHeader.setText("Product");

            }

            return true;
        });




    }

    public void ReplaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments,fragment);
        fragmentTransaction.commit();
    }
}