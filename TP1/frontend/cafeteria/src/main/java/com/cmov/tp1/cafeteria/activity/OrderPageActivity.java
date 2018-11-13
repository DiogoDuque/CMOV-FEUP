package com.cmov.tp1.cafeteria.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmov.tp1.cafeteria.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import core.CafeteriaOrder;
import core.CafeteriaOrderAdapter;
import core.CafeteriaOrderProduct;
import core.CafeteriaOrderProductAdapter;
import core.MyClickListener;
import networking.HTTPRequestUtility;
import networking.NetworkRequests;

public class OrderPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        final RecyclerView recyclerView = findViewById(R.id.products_list);

        Bundle b = getIntent().getExtras();
        int orderId = b.getInt("id");
        String date = b.getString("date");
        double price = b.getDouble("price");

        TextView order = findViewById(R.id.order_label);
        order.setText("Order #" + orderId);

        TextView total = findViewById(R.id.total_label);
        total.setText(Double.toString(price));

        NetworkRequests.getOrderProducts(this, orderId, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<CafeteriaOrderProduct> productList = CafeteriaOrderProductAdapter.parseJsonProducts(json);
                CafeteriaOrderProductAdapter adapter = new CafeteriaOrderProductAdapter(productList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
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
