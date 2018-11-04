package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.ToolbarUtility;

public class VoucherPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_info);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Button useVoucherButton = findViewById(R.id.use_voucher);
        useVoucherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
            }
        });
    }

    private void makeOrder(){
        Intent intent = new Intent(this, MakeOrderActivity.class);
        startActivity(intent);
    }
}
