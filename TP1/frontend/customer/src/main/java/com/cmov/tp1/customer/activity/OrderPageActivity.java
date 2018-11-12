package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.core.CafeteriaOrderProductAdapter;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.List;

public class OrderPageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shows);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Bundle b = getIntent().getExtras();

        TextView label = findViewById(R.id.label);
        label.setText("Order #" + b.getInt("orderId"));

        TextView date = findViewById(R.id.date);
        date.setText(b.getString("date"));

        TextView price = findViewById(R.id.price);
        price.setText(Double.toString(b.getDouble("price")));

        final RecyclerView recyclerView = findViewById(R.id.products_list);

        NetworkRequests.getOrderProducts(this, b.getInt("orderId"), new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<CafeteriaOrderProduct> products = CafeteriaOrderProductAdapter.parseJsonProducts(json);
                CafeteriaOrderProductAdapter adapter = new CafeteriaOrderProductAdapter(products);
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
