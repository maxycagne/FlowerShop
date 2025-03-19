package com.example.flowershop.Activities.All;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowershop.Activities.All.Register;
import com.example.flowershop.Activities.Customer.CustomerHome;
import com.example.flowershop.Activities.Vendor.VendorHome;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.User;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityLoginBinding;

import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding root;

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
        root = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setDb();



        root.btnLogin.setOnClickListener(v -> {
            login();

        });

        root.btnRegister.setOnClickListener(v -> startActivity(new Intent(this, Register.class)));


    }

    public void login()
    {
        if(validate())
        {
            return;
        }
        String username = root.edtUsername.getText().toString();
        String password = root.edtPassword.getText().toString();
        Executors.newSingleThreadExecutor().submit(()->{
            User user = dbHelper.getUserDao().getAllUser(username,password);
            if(user != null)
            {
                if(user.getPriv().equals("Vendor"))
                {
                    handler.post(()->{
                        Toast.makeText(this, "Welcome" + user.getFirstName(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, VendorHome.class));
                        finish();
                    });

                }
                else if (user.getPriv().equals("User"))
                {

                    handler.post(()->{
                        Toast.makeText(this, "Welcome" + user.getFirstName(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, CustomerHome.class));
                        finish();
                    });
                }
            }
            else
            {
                handler.post(()->{
                    Toast.makeText(this, "Wrong username or password. try again.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public boolean validate()
    {
        boolean validate = true;
        if(root.edtUsername.getText().toString().equals(""))
        {
            root.layoutEdtUsername.setError("Fill up username");
            root.layoutEdtUsername.setErrorEnabled(true);
            validate = true;
        }
        else {
            root.layoutEdtUsername.setError("");
            root.layoutEdtUsername.setErrorEnabled(false);
            validate = false;
        }

        if(root.edtPassword.getText().toString().equals(""))
        {
            root.layoutEdtPassword.setError("Fill up password");
            root.layoutEdtPassword.setErrorEnabled(true);
            validate = true;
        }
        else {
            root.layoutEdtPassword.setError("");
            root.layoutEdtPassword.setErrorEnabled(false);
            validate = false;
        }
        return validate;
    }

}