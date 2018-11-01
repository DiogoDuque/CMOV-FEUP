package com.cmov.tp1.cafeteria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }
}
