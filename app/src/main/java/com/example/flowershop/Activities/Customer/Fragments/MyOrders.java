package com.example.flowershop.Activities.Customer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.R;
import com.example.flowershop.databinding.FragmentCustomerHomepageBinding;
import com.example.flowershop.databinding.FragmentMyOrdersBinding;


public class MyOrders extends Fragment {

    private FragmentMyOrdersBinding root;
    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(getContext());
    }


    public MyOrders() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDb();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = FragmentMyOrdersBinding.inflate(inflater,container,false);





        return root.getRoot();
    }
}