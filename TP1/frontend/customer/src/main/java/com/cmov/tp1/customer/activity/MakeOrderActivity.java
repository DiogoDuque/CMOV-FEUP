package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmov.tp1.customer.R;

public class MakeOrderActivity extends AppCompatActivity {

    private int quantity = 0;
    private double totalToPay = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        addValuesToSpinner();

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
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    private void addValuesToSpinner(){
        //Exemplo
        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner s = (Spinner) findViewById(R.id.products);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }


    private void finishPurchase(){
        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
    }

    private void addProduct(){
        Spinner spinner = (Spinner) findViewById(R.id.products);
        int productID = Integer.valueOf(spinner.getSelectedItemPosition());
        int quantity = Integer.parseInt(findViewById(R.id.quantity_label).toString());

        if(quantity < 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        totalToPay += quantity * 0;
    }

    private void changeQuantity(boolean type){
        if(type)
            quantity++;
        else
            quantity--;
    }
}
