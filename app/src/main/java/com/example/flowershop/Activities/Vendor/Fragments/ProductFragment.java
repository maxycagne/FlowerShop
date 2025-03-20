package com.example.flowershop.Activities.Vendor.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.flowershop.Activities.Vendor.AddProduct;
import com.example.flowershop.Activities.Vendor.EditProduct;
import com.example.flowershop.Activities.Vendor.VendorHome;
import com.example.flowershop.Adapter.OrderDetailsAdapter;
import com.example.flowershop.Adapter.ProductAdapter;
import com.example.flowershop.Database.Dao.FlowerDao;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.R;
import com.example.flowershop.databinding.FragmentHomeBinding;
import com.example.flowershop.databinding.FragmentProductBinding;

import java.util.List;
import java.util.concurrent.Executors;


public class ProductFragment extends Fragment {

    private FragmentProductBinding root;
    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(getContext());
    }

    public ProductFragment() {
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
        root = FragmentProductBinding.inflate(inflater,container,false);


        String[] quantity = {"Low Quantity","High Quantity"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,quantity);
        root.spnFilter.setAdapter(adapter);
        root.spnFilter.setOnClickListener(v -> {
            root.spnFilter.showDropDown();
        });

        retrieve();

        root.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddProduct.class));
        });


        return root.getRoot();
    }

    public void retrieve()
    {
        Executors.newSingleThreadExecutor().submit(()->{
            FlowerDao flowerDao = ((VendorHome) getActivity()).getDbHelper().getFlowerDao();
            List<Flower> flowerList = flowerDao.getAllFlower();



            ((VendorHome) getActivity()).getHandler().post(()->
            {
                if(flowerList == null)
                {

                }
                else
                {
                    ProductAdapter productAdapter = new ProductAdapter(getContext(), flowerList, new ProductAdapter.FlowerClick() {
                        @Override
                        public void OnClick(Flower flower) {
                            startActivity(new Intent(getContext(), EditProduct.class).putExtra("name",flower.getFlowerName())
                                    .putExtra("price",flower.getPrice()).putExtra("quantity",flower.getQuantity()).putExtra("id",flower.getId()));
                        }
                    });
                    root.lstFlowers.setAdapter(productAdapter);
                }
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