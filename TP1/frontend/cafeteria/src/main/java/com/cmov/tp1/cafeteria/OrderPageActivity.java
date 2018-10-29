package com.cmov.tp1.cafeteria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OrderPageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        ListView list = findViewById(R.id.product_list);
        /**adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rests);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);**/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
