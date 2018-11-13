package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.cmov.tp1.customer.core.CafeteriaOrderProduct.productsToJson;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String TAG = "MakeOrderActivity";
    private ArrayList<CafeteriaOrderProduct> products = new ArrayList<>();
    private String[] productsStrList = new String[0];
    private int quantityCounter = 1;
    private double totalToPay = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
            }
        });

        addValuesToSpinner();

        setupSelectedProductsList();

        Button minusButton = findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(false);
            }
        });

        Button plusButton = findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(true);
            }
        });

        Button addProductButton = findViewById(R.id.addproduct_button);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        Button finishButton = findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishPurchase();
            }
        });
    }

    private void setupSelectedProductsList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, productsStrList);
        ListView listView = findViewById(R.id.product_list);
        listView.setAdapter(adapter);
    }

    private void addValuesToSpinner(){
        Spinner spinner = findViewById(R.id.products);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.products, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void finishPurchase(){
        if(products.size()==0) {
            Toast.makeText(this, "No product selected.", Toast.LENGTH_SHORT).show();
            return;
        }
        Date date = new Date();
        NetworkRequests.makeOrder(this, date, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    int orderId = json.getInt("id");
                    Intent intent = new Intent(MakeOrderActivity.this, OrderFinishedActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("orderID", orderId);
                    b.putString("products", productsToJson(products));
                    b.putStringArrayList("strings", new ArrayList<>(Arrays.asList(productsStrList)));
                    intent.putExtras(b);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle
            }
        });
    }

    private void addProduct(){
        Spinner spinner = findViewById(R.id.products);
        final String productName = spinner.getSelectedItem().toString();
        TextView quantityLabel = findViewById(R.id.quantity_label);
        final int quantity = Integer.parseInt(quantityLabel.getText().toString());

        if(quantity <= 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkRequests.getProductPrice(this, productName, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    final double price = Double.parseDouble(json.getString("price"));
                    totalToPay += quantity * price;
                    TextView total = findViewById(R.id.total);
                    total.setText(String.format("%.2f", totalToPay));

                    boolean productAlreadyExists = false;
                    for(CafeteriaOrderProduct prod: products) {
                        if(prod.getName().equals(productName)) {
                            productAlreadyExists = true;
                            prod.addQuantity(quantity);
                            break;
                        }
                    }
                    if(!productAlreadyExists) {
                        products.add(new CafeteriaOrderProduct(productName, price, quantity));
                    }
                    updateProductListView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle
            }
        });
    }

    private void updateProductListView() {
        List<String> productsStrListTmp = new ArrayList<>();
        for(CafeteriaOrderProduct prod: products) {
            productsStrListTmp.add(String.format("%dx %.2f - %s", prod.getQuantity(), prod.getPrice(), prod.getName()));
        }
        productsStrList = productsStrListTmp.toArray(new String[productsStrListTmp.size()]);
        Log.d(TAG, "productsStr: "+productsStrListTmp.toString());
        setupSelectedProductsList();
        resetQuantity();
    }

    private void changeQuantity(boolean type){
        if(type)
            quantityCounter++;
        else{
            if(quantityCounter <= 1){
                Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            quantityCounter--;
        }

        TextView quant = findViewById(R.id.quantity_label);
        quant.setText(Integer.toString(quantityCounter));
    }

    private void resetQuantity() {
        TextView quant = findViewById(R.id.quantity_label);
        quant.setText("1");
        quantityCounter = 1;
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
