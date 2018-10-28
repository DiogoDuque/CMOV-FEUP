package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.cmov.tp1.customer.R;

public class MakeOrderActivity extends AppCompatActivity {

    private int quantity = 0;
    private double totalToPay = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

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

        Button addProductButton = findViewById(R.id.addproduct_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }


    private void finishPurchase(){

        setContentView(R.layout.activity_shows);
    }

    private void addProduct(){
        Spinner spinner = (Spinner) findViewById(R.id.products);
        int productID = Integer.valueOf(spinner.getSelectedItemPosition());
        int quantity = Integer.parseInt(findViewById(R.id.quantity_label).toString());
    }

    private void changeQuantity(boolean type){
        if(type)
            quantity++;
        else
            quantity--;
    }
}
