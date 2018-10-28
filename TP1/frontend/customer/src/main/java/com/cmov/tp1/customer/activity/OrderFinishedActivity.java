package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;

public class OrderFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished_cafeteria);

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
            intent = new Intent(this, VoucherPageActivity.class);
        else
            intent = new Intent(this, OrderFinishedActivity.class);

        startActivity(intent);
    }
}
