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

import com.example.flowershop.Activities.Customer.CustomerHome;
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




        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String contactNum = intent.getStringExtra("contactnum");
        String address = intent.getStringExtra("address");
        String flowerName = intent.getStringExtra("flowerName");
        int quantity = intent.getIntExtra("quantity", 0);
        double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
        String orderDate = intent.getStringExtra("orderDate");

        root.edtFirstName.setText(name);
        root.edtContactNumber.setText(contactNum);
        root.edtAddress.setText(address);
        root.txtOrderDate.setText(orderDate);
        root.txtTotalPrice.setText(String.valueOf(totalPrice));
        root.flowerName.setText(flowerName);
        root.quantity.setText(String.valueOf(quantity));

        root.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this,VendorHome.class));
        });

        root.btnCancelOrder.setOnClickListener(v -> {
            String status = "Cancelled";
            cancel(status,id);
        });

        root.btnAcceptOrder.setOnClickListener(v->{
            String status = "Completed";
            cancel(status,id);
        });


    }

    public void cancel(String status,int id)
    {
        Executors.newSingleThreadExecutor().submit(()->{
            dbHelper.orderDetailsDao.updateOrder(status,id);
            handler.post(()->{
                Toast.makeText(this, "Order Status updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CustomerHome.class));
                finish();
            });
        });

    }

    public void onResume()
    {
        super.onResume();

    }
}