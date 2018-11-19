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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class OrderPageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        Bundle b = getIntent().getExtras();

        TextView label = findViewById(R.id.label);
        label.setText("Order #" + b.getInt("orderId"));

        TextView date = findViewById(R.id.date);
        date.setText(b.getString("date"));

        TextView price = findViewById(R.id.price);
        price.setText(Double.toString(b.getDouble("price")) + "$");

        final RecyclerView recyclerView = findViewById(R.id.products_list);

        NetworkRequests.getOrderProducts(this, b.getInt("orderId"), new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                try {
                    final HashMap<String, Integer> productsMap = new HashMap<>();
                    JSONArray array = json.getJSONArray("result");
                    for(int i=0; i<array.length(); i++) {
                        String name = array.getJSONObject(i).getString("name");
                        if(productsMap.containsKey(name)) {
                            productsMap.put(name, productsMap.get(name)+1);
                        } else productsMap.put(name, 1);
                    }
                    ArrayList<CafeteriaOrderProduct> products = new ArrayList<>();
                    for(Map.Entry<String, Integer> pair: productsMap.entrySet()) {
                        products.add(new CafeteriaOrderProduct(pair.getKey(), pair.getValue()));
                    }

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }

}
