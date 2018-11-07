package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String TAG = "MakeOrderActivity";
    private ArrayList<Integer> products = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private int quantity = 0;
    private double totalToPay = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "????");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        addValuesToSpinner();

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
    }

    private void addValuesToSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.products);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.products, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void finishPurchase(){
        Date date = new Date();
        final int[] orderId = new int[1];
        NetworkRequests.makeOrder(this, date, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    orderId[0] = json.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject json) {
            }
        });

        Intent intent = new Intent(this, OrderFinishedActivity.class);

        Bundle b = new Bundle();
        b.putInt("orderID", orderId[0]);
        b.putIntegerArrayList("products", products);
        b.putIntegerArrayList("quantities", quantities);
        intent.putExtras(b); //Put your id to your next Intent

        startActivity(intent);
    }

    private void addProduct(){
        Spinner spinner = (Spinner) findViewById(R.id.products);
        int productID = Integer.valueOf(spinner.getSelectedItemPosition());
        final int quantity = Integer.parseInt(findViewById(R.id.quantity_label).toString());

        if(quantity < 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        products.add(productID);
        quantities.add(quantity);

        NetworkRequests.getProductPrice(this, productID, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    final double price = json.getDouble("price");
                    totalToPay += quantity * price;
                    TextView total = findViewById(R.id.total);
                    total.setText(Double.toString(totalToPay));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject json) {
            }
        });
    }

    private void changeQuantity(boolean type){
        if(type)
            quantity++;
        else{
            if(quantity == 0){
                Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            quantity--;
        }

        TextView quant = findViewById(R.id.quantity_label);
        quant.setText(Integer.toString(quantity));
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
