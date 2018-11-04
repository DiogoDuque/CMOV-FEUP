package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.ToolbarUtility;

public abstract class CafeteriaOrdersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_show_ticket);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);
    }
}
