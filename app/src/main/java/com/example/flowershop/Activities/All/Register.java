package com.example.flowershop.Activities.All;

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

import com.example.flowershop.Activities.All.Login;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.User;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityRegisterBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding root;
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
        root = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setDb();

        root.btnBack.setOnClickListener(v ->
        {
            startActivity(new Intent(this, Login.class));
            finish();
        });

        root.btnRegister.setOnClickListener(v -> {
            register();
        });
    }

    public void register()
    {
        if(!validate())
        {
            return;
        }

        String username = root.edtUsername.getText().toString();
        String firstName = root.edtFirstName.getText().toString();
        String middle = root.edtMiddleName.getText().toString();
        String lastName = root.edtLastName.getText().toString();
        String address = root.edtAddress.getText().toString();
        String contactNum = root.edtContactNumber.getText().toString();
        String password = root.edtPassword.getText().toString();
        String priv = "User";


        Executors.newSingleThreadExecutor().submit(()->{
            User user = new User(username,firstName,middle,lastName,contactNum,password,address,priv);
            dbHelper.getUserDao().insertAllUser(user);

            handler.post(()->{
                Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                finish();
            });
        });
    }

    public boolean validate()
    {
        boolean validate = true;

        if(root.edtFirstName.getText().toString().equals(""))
        {
            root.layoutEdtFirstName.setError("Fill up first name");
            root.layoutEdtFirstName.setErrorEnabled(true);
            validate = false;
        }
        else {
            root.layoutEdtFirstName.setError("");
            root.layoutEdtFirstName.setErrorEnabled(false);

        }


        if(root.edtLastName.getText().toString().equals(""))
        {
            root.layoutEdtLastName.setError("Fill up last name");
            root.layoutEdtLastName.setErrorEnabled(true);
            validate = false;
        }
        else {
            root.layoutEdtLastName.setError("");
            root.layoutEdtLastName.setErrorEnabled(false);

        }


        if(root.edtAddress.getText().toString().equals(""))
        {
            root.layoutEdtAddress.setError("Fill up address");
            root.layoutEdtAddress.setErrorEnabled(true);
            validate = false;

        }
        else {
            root.layoutEdtAddress.setError("");
            root.layoutEdtAddress.setErrorEnabled(false);
        }


        if(root.edtContactNumber.getText().toString().equals(""))
        {
            root.layoutEdtContactNumber.setError("Fill up contact number");
            root.layoutEdtContactNumber.setErrorEnabled(true);
            validate = false;

        }
        else {
            root.layoutEdtContactNumber.setError("");
            root.layoutEdtContactNumber.setErrorEnabled(false);
        }

        if(root.edtUsername.getText().toString().equals(""))
        {
            root.layoutEdtUsername.setError("Fill up username");
            root.layoutEdtUsername.setErrorEnabled(true);
            validate = false;

        }
        else {
            root.layoutEdtUsername.setError("");
            root.layoutEdtUsername.setErrorEnabled(false);
        }

        if(root.edtPassword.getText().toString().equals(""))
        {
            root.layoutEdtPassword.setError("Fill up password");
            root.layoutEdtPassword.setErrorEnabled(true);
            validate = false;
        }
        else {
            root.layoutEdtPassword.setError("");
            root.layoutEdtPassword.setErrorEnabled(false);
        }

        if(root.edtConfirmPassword.getText().toString().equals(""))
        {
            root.layoutEdtConfirmPassword.setError("Fill up confirm password");
            root.layoutEdtConfirmPassword.setErrorEnabled(true);
            validate = false;

        }
        else {
            root.layoutEdtConfirmPassword.setError("");
            root.layoutEdtConfirmPassword.setErrorEnabled(false);

        }


        if(!root.edtPassword.getText().toString().equals(root.edtConfirmPassword.getText().toString()))
        {
            root.layoutEdtConfirmPassword.setError("Confirm password does not match password");
            root.layoutEdtConfirmPassword.setErrorEnabled(true);
            validate = false;
        }
        else {
            root.layoutEdtConfirmPassword.setError("");
            root.layoutEdtConfirmPassword.setErrorEnabled(false);


        }

        return validate;


    }
}