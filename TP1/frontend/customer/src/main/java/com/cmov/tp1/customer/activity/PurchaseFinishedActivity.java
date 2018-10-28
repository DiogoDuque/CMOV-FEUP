package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;

public class PurchaseFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_finished);

        Button makeOrderButton = findViewById(R.id.make_order);
        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_make_order);
            }
        });

        Button voucherButton = findViewById(R.id.see_voucher);
        voucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_voucher_info);
            }
        });
    }
}
