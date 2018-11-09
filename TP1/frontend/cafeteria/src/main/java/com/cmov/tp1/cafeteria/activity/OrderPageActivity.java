package com.cmov.tp1.cafeteria.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cmov.tp1.cafeteria.R;

import java.util.ArrayList;
import java.util.List;

public class OrderPageActivity extends AppCompatActivity {

    //Só para teste
    List<String> products = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        ListView list = findViewById(R.id.product_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        list.setAdapter(adapter);

        Button minusButton = findViewById(R.id.validate_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateVouchers();
            }
        });
    }

    private void validateVouchers(){
        Intent intent = new Intent(this, VouchersPageActivity.class);
        startActivity(intent);
    }
}
