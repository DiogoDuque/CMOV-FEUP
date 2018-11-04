package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.ToolbarUtility;

public class PurchaseFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_finished);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Button makeOrderButton = findViewById(R.id.make_order);
        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout(true);
            }
        });

        Button voucherButton = findViewById(R.id.see_voucher);
        voucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout(false);
            }
        });
    }

    private void changeLayout(boolean type){
        Intent intent;
        if(type)
            intent = new Intent(this, MakeOrderActivity.class);
        else
            intent = new Intent(this, VoucherPageActivity.class);

        startActivity(intent);
    }
}
