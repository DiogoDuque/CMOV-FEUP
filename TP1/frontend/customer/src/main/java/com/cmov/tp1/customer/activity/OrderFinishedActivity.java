package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import java.util.ArrayList;

public class OrderFinishedActivity extends AppCompatActivity {
    private int orderId;
    private ArrayList<Integer> products;
    private ArrayList<Integer> quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished_cafeteria);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Bundle b = getIntent().getExtras();
        orderId = b.getInt("orderID");
        products = b.getIntegerArrayList("products");
        quantities = b.getIntegerArrayList("quantities");

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

        Bundle b = new Bundle();
        b.putInt("orderID", orderId);
        b.putIntegerArrayList("products", products);
        b.putIntegerArrayList("quantities", quantities);
        intent.putExtras(b);

        startActivity(intent);
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
