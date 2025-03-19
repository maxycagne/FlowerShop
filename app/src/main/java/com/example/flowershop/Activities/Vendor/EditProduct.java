package com.example.flowershop.Activities.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityEditProductBinding;

import java.util.List;
import java.util.concurrent.Executors;

import kotlinx.coroutines.flow.Flow;

public class EditProduct extends AppCompatActivity {

    private ActivityEditProductBinding root;

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
        root = ActivityEditProductBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price",0.0);
        int quantity = getIntent().getIntExtra("quantity",0);
        int id = getIntent().getIntExtra("id",0);

        root.edtProductName.setText(name);
        root.edtPrice.setText(String.valueOf(price));
        root.edtQuantity.setText(String.valueOf(quantity));


        root.btnUpdate.setOnClickListener(v->{
            update(id);
        });


        root.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this,VendorHome.class));
        });




    }


    public void update(int id)
    {
        validate();

        String name = root.edtProductName.getText().toString();
        double price = Double.parseDouble(root.edtPrice.getText().toString());
        int quantity = Integer.parseInt(root.edtQuantity.getText().toString());

        Executors.newSingleThreadExecutor().submit(()->{

            Flower flower = dbHelper.getFlowerDao().findFlowerbyId(id);

            dbHelper.flowerDao.updateFlower(name,quantity,price,id);



            handler.post(()->{
                Toast.makeText(this,flower.getId() + " Flower updated sucessfully.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,VendorHome.class));
            });
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