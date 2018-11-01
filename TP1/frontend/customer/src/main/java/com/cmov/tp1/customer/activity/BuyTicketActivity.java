package com.cmov.tp1.customer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;

public class BuyTicketActivity extends AppCompatActivity {

    private int quantity = 0;
    private double totalToPay = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_show_ticket);

        Button minusButton = findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(false);
            }
        });

        Button plusButton = findViewById(R.id.minus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(true);
            }
        });
    }

    private void finishPurchase(){
        Spinner spinner = (Spinner) findViewById(R.id.show_buy_spinner);
        int showID = Integer.valueOf(spinner.getSelectedItemPosition());
        int quantity = Integer.parseInt(findViewById(R.id.quantity_label).toString());

        if(quantity < 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
    }

    private void changeQuantity(boolean type){
        if(type)
            quantity++;
        else
            quantity--;

        totalToPay = quantity * 0;

        TextView quantityText = (TextView)findViewById(R.id.quantity_label);
        quantityText.setText(quantity);

        TextView TotalText = (TextView)findViewById(R.id.total);
        TotalText.setText((int) totalToPay);

        startActivity(new Intent(this, BuyTicketActivity.class));
    }
}
