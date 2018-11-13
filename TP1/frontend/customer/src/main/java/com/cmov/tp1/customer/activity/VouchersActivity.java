package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Voucher;
import com.cmov.tp1.customer.core.VouchersAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VouchersActivity extends AppCompatActivity {
    private static final String TAG = "VouchersActivity";


    VouchersAdapter unusedVouchersAdapter;
    VouchersAdapter usingVouchersAdapter;
    private ArrayList<CafeteriaOrderProduct> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> productsStr = bundle.getStringArrayList("strings");
        final int orderId = bundle.getInt("orderID");
        products = CafeteriaOrderProduct.jsonToProducts(bundle.getString("products"));

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
            }
        });

        final RecyclerView unusedVouchers = findViewById(R.id.unused_vouchers);
        final RecyclerView usingVouchers = findViewById(R.id.using_vouchers);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, productsStr);
        ListView listView = findViewById(R.id.products);
        listView.setAdapter(adapter);

        Button buyButton = findViewById(R.id.cafeteria_buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Voucher> vouchers = usingVouchersAdapter.getVouchers();
                HashMap<String, Integer> productQuantityMap = new HashMap<>();
                // init map
                for(CafeteriaOrderProduct prod: products) {
                    productQuantityMap.put(prod.getName(), prod.getQuantity());
                }

                // check if vouchers are valid for the map
                for(Voucher v: vouchers) {
                    if(!v.isFreeProduct()) // if discount, always valid
                        continue;
                    String product = v.getProduct();
                    if(productQuantityMap.containsKey(product)) {
                        int quantity = productQuantityMap.get(product) -1;
                        if(quantity >= 0) {
                            productQuantityMap.put(product, quantity);
                            continue;
                        }
                    }
                    Toast.makeText(VouchersActivity.this, "One of the chosen vouchers is invalid.", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetworkRequests.addToOrder(VouchersActivity.this, orderId, products, vouchers, new HTTPRequestUtility.OnRequestCompleted() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        Toast.makeText(VouchersActivity.this, "Order was placed and will be validated. Check the waiter for more details.",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(JSONObject json) {
                        //TODO handle error
                    }
                });
            }
        });

        NetworkRequests.getMyVouchers(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                final List<Voucher> voucherList = VouchersAdapter.parseJsonVouchers(json);
                unusedVouchersAdapter = new VouchersAdapter(voucherList);
                unusedVouchersAdapter.setupBoilerplate(getApplicationContext(), unusedVouchers, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if(usingVouchersAdapter.getVouchersSize() >= 2) {
                            Toast.makeText(VouchersActivity.this,
                                    "Cannot use more than 2 vouchers in a single order!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Voucher voucher = unusedVouchersAdapter.removeVoucher(position);
                        usingVouchersAdapter.addVoucher(voucher);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });
                usingVouchersAdapter = new VouchersAdapter(new ArrayList<Voucher>());
                usingVouchersAdapter.setupBoilerplate(getApplicationContext(), usingVouchers, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Voucher voucher = usingVouchersAdapter.removeVoucher(position);
                        unusedVouchersAdapter.addVoucher(voucher);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                unusedVouchers.setAdapter(unusedVouchersAdapter);
                usingVouchers.setAdapter(usingVouchersAdapter);
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
