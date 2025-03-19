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

import com.example.flowershop.Activities.Vendor.VendorHome;
import com.example.flowershop.Activities.Vendor.VendorViewOrder;
import com.example.flowershop.Adapter.OrderDetailsAdapter;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.R;
import com.example.flowershop.databinding.ActivityLoginBinding;
import com.example.flowershop.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding root;

    private Handler handler;

    private DbHelper dbHelper;

    public void setDb()
    {
        handler = new Handler(Looper.getMainLooper());
        dbHelper = new DbHelper(getContext());
    }

    public HomeFragment() {
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
        // Inflate the layout for this fragment
        root = FragmentHomeBinding.inflate(inflater,container,false);

        String[] status = {"Pending","Complete","Cancelled"};

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,status);
        root.spnFilter.setAdapter(statusAdapter);
        root.spnFilter.setOnClickListener(v -> {
            root.spnFilter.showDropDown();
        });


        retrieve();



        return root.getRoot();
    }

    public void retrieve()
    {
        Executors.newSingleThreadExecutor().submit(()->{
            OrderDetailsDao orderDetailsDao = ((VendorHome) getActivity()).getDbHelper().getOrderDetailsDao();
            List<OrderDetails> orderDetailsList = orderDetailsDao.getAllOrderDetails();

            ((VendorHome) getActivity()).getHandler().post(()->
            {
                if(orderDetailsDao == null)
                {

                }
                else
                {
                    OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(getContext(), orderDetailsList, new OrderDetailsAdapter.OrderDetailsClick() {
                        @Override
                        public void Onclick(OrderDetails orderDetails) {
                            startActivity(new Intent(getContext(), VendorViewOrder.class).putExtra("name",orderDetails.getName())
                                    .putExtra("contactnum",orderDetails.getContactNum()).putExtra("address",orderDetails.getAddress()));

                        }
                    });
                    root.lstOrders.setAdapter(orderDetailsAdapter);
                }
            });
        });
    }

    public void onResume()
    {
        super.onResume();
        retrieve();

    }
}