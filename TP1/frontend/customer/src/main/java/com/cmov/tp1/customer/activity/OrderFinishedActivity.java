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
import android.widget.Button;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import java.util.ArrayList;

public class OrderFinishedActivity extends AppCompatActivity {
    private static final String TAG = "OrderFinishedActivity";
    private int orderId;
    private ArrayList<CafeteriaOrderProduct> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished_cafeteria);

        Bundle b = getIntent().getExtras();
        orderId = b.getInt("orderID");
        products = CafeteriaOrderProduct.jsonToProducts(b.getString("products"));

        Button noButton = findViewById(R.id.no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wantVoucher(false);
            }
        });

        Button yesButton = findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wantVoucher(true);
            }
        });
    }

    private void wantVoucher(Boolean answer){
        Intent intent;
        if(answer)
            intent = new Intent(this, VouchersActivity.class);
        else
            intent = new Intent(this, MakeOrderActivity.class);

        intent.putExtras(getIntent().getExtras()); // use the same received bundle

        startActivity(intent);
    }
}
