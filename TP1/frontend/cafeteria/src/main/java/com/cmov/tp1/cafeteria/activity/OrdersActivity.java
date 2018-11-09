package com.cmov.tp1.cafeteria.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.cmov.tp1.cafeteria.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Só para teste
    List<String> orders = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ListView list = findViewById(R.id.orders_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orders);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        Spinner spinner = findViewById(R.id.orders_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.order_by, android.R.layout.activity_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Object item = parent.getItemAtPosition(pos);
                System.out.println(item.toString());     //prints the text in spinner item.

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Como ir buscar a posição do sítio clicado
        String name = orders.get(position);

    }
}
