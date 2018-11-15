package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.ToolbarUtility;

public class VoucherPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_info);

        Bundle bundle = getIntent().getExtras();

        TextView productLabel = findViewById(R.id.product_label);
        String product = bundle.getString("product");
        productLabel.setText(product == null ? "" : product);
        TextView typeLabel = findViewById(R.id.type_discount_label);
        typeLabel.setText(bundle.getString("type"));
        TextView statusLabel = findViewById(R.id.status_label);
        statusLabel.setText(Boolean.parseBoolean(bundle.getString("isUsed")) ? "Used" : "Not used");
    }
}
