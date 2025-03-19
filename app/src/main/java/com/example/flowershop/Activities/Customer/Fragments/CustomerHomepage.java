package com.example.flowershop.Activities.Customer.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.flowershop.Activities.Customer.CustomerHome;
import com.example.flowershop.Activities.Customer.ViewProductPage;
import com.example.flowershop.Activities.Vendor.EditProduct;
import com.example.flowershop.Activities.Vendor.VendorHome;
import com.example.flowershop.Activities.Vendor.VendorViewOrder;
import com.example.flowershop.Adapter.OrderDetailsAdapter;
import com.example.flowershop.Adapter.ProductAdapter;
import com.example.flowershop.Adapter.ProductShowAdapter;
import com.example.flowershop.Database.Dao.FlowerDao;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.R;
import com.example.flowershop.databinding.FragmentCustomerHomepageBinding;
import com.example.flowershop.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.concurrent.Executors;


public class CustomerHomepage extends Fragment {

    private FragmentCustomerHomepageBinding root;
    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(getContext());
    }


    public CustomerHomepage() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDb();
        retrieve();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = FragmentCustomerHomepageBinding.inflate(inflater,container,false);

        root.spnFilter.setText("All");

        String[] price = {"All","Daisy","Rose","Sunflower","Tulip","Peony"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,price);
        root.spnFilter.setAdapter(adapter);
        root.spnFilter.setOnClickListener(v -> {
            root.spnFilter.showDropDown();
        });

        root.spnFilter.setOnItemClickListener(((parent, view, position, id) -> {
            retrieve();
        }));



        return root.getRoot();
    }

    public void retrieve()
    {
        Executors.newSingleThreadExecutor().submit(()->{
            FlowerDao flowerDao = ((CustomerHome) getActivity()).getDbHelper().getFlowerDao();
            List<Flower> flowerList;

            if("All".equals(root.spnFilter.getText().toString()))
            {
                flowerList = flowerDao.getAllFlower();
            }
            else
            {
                flowerList = flowerDao.getAllByFlowerName(root.spnFilter.getText().toString());
            }

            ((CustomerHome) getActivity()).getHandler().post(()->
            {
                ProductShowAdapter adapter = new ProductShowAdapter(getContext(), flowerList, new ProductShowAdapter.FlowerClick() {
                    @Override
                    public void OnClick(Flower flower) {
                        startActivity(new Intent(getContext(), ViewProductPage.class).putExtra("name",flower.getFlowerName())
                                .putExtra("price",flower.getPrice()));
                    }
                });
                root.lstFlowers.setAdapter(adapter);


            });
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        retrieve();

    }
}