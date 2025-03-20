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
import com.example.flowershop.Activities.Customer.CustomerViewOrder;
import com.example.flowershop.Activities.Customer.ViewProductPage;
import com.example.flowershop.Activities.Vendor.VendorHome;
import com.example.flowershop.Activities.Vendor.VendorViewOrder;
import com.example.flowershop.Adapter.OrderDetailsAdapter;
import com.example.flowershop.Database.Dao.OrderDetailsDao;
import com.example.flowershop.Database.DbHelper;
import com.example.flowershop.Database.Entity.Flower;
import com.example.flowershop.Database.Entity.OrderDetails;
import com.example.flowershop.R;
import com.example.flowershop.databinding.FragmentCustomerHomepageBinding;
import com.example.flowershop.databinding.FragmentMyOrdersBinding;

import java.util.List;
import java.util.concurrent.Executors;


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
        retrieve();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = FragmentMyOrdersBinding.inflate(inflater,container,false);




        root.spnFilter.setText("All");

        String[] status = {"All","Pending","Cancelled","Completed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,status);
        root.spnFilter.setAdapter(adapter);
        root.spnFilter.setOnClickListener(v -> {
            root.spnFilter.showDropDown();
        });

        root.spnFilter.setOnItemClickListener(((parent, view, position, id) -> {
            retrieve();
        }));

        retrieve();


        return root.getRoot();
    }

    public void retrieve()
    {
        Executors.newSingleThreadExecutor().submit(()->{
            OrderDetailsDao orderDetailsDao = ((CustomerHome) getActivity()).getDbHelper().getOrderDetailsDao();
            List<OrderDetails> orderDetailsList;

            if("All".equals(root.spnFilter.getText().toString()))
            {
                orderDetailsList = orderDetailsDao.getAllOrderDetails();
            }
            else
            {
                orderDetailsList = orderDetailsDao.getAllByOrderDetailsStatus(root.spnFilter.getText().toString());
            }

            ((CustomerHome) getActivity()).getHandler().post(()->
            {
                if(orderDetailsList.isEmpty())
                {
                    root.lstOrderHistory.setVisibility(View.GONE);
                    root.txtNoOrder.setVisibility(View.VISIBLE);
                }
                else
                {
                    root.lstOrderHistory.setVisibility(View.VISIBLE);
                    root.txtNoOrder.setVisibility(View.GONE);
                    OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(getContext(), orderDetailsList, new OrderDetailsAdapter.OrderDetailsClick() {
                        @Override
                        public void Onclick(OrderDetails orderDetails) {
                            startActivity(new Intent(getContext(), CustomerViewOrder.class).putExtra("name",orderDetails.getName()).putExtra("id",orderDetails.getId())
                                    .putExtra("contactnum",orderDetails.getContactNum()).putExtra("address",orderDetails.getAddress()).putExtra("flowerName",orderDetails.getFlowerName())
                                    .putExtra("quantity",orderDetails.getQuantity()).putExtra("totalPrice",orderDetails.getTotalAmount()).putExtra("orderDate",orderDetails.getDate()));

                        }
                    });
                    root.lstOrderHistory.setAdapter(orderDetailsAdapter);
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