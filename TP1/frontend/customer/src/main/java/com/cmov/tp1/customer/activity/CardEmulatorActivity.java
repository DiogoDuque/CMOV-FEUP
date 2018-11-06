package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cmov.tp1.customer.R;

public class CardEmulatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_emulator);

        TextView dateLabel = findViewById(R.id.show_date);
        dateLabel.setText("Show Date");

        TextView priceLabel = findViewById(R.id.show_date);
        priceLabel.setText("Show Price");

        TextView showLabel = findViewById(R.id.show_name);
        priceLabel.setText("Show Price");
    }
}
