package com.cmov.tp1.cafeteria.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.cmov.tp1.cafeteria.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import core.CafeteriaOrder;
import core.CafeteriaOrderAdapter;
import core.MyClickListener;
import networking.HTTPRequestUtility;
import networking.NetworkRequests;

public class OrdersActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        final RecyclerView recyclerView = findViewById(R.id.orders_list);

        NetworkRequests.getOrders(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<CafeteriaOrder> ordersList = CafeteriaOrderAdapter.parseJsonOrders(json);
                CafeteriaOrderAdapter adapter = new CafeteriaOrderAdapter(ordersList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        CafeteriaOrder order = ordersList.get(position);

                        Intent intent = new Intent(OrdersActivity.this, OrderPageActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", order.getId());
                        b.putString("date", order.getDate());
                        b.putDouble("price", order.getPrice());
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onError(JSONObject json) {
                 //TODO handle error
            }
        });
    }

}
