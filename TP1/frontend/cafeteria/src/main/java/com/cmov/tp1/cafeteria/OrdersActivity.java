package com.cmov.tp1.cafeteria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

public class OrdersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ListView list = findViewById(R.id.orders_list);
        /**adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rests);
         list.setAdapter(adapter);
         list.setOnItemClickListener(this);**/

        Spinner spinner = findViewById(R.id.orders_selector);
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
        /**Restaurant r = rests.get(pos);

        ((EditText)findViewById(R.id.ed_name)).setText(r.getName());
        ((EditText)findViewById(R.id.ed_address)).setText(r.getAddress());
        RadioGroup types = findViewById(R.id.rg_types);

        if (r.getType().equals("sit")) {
            types.check(R.id.rb_sit);
        }
        else if (r.getType().equals("take")) {
            types.check(R.id.rb_take);
        }
        else {
            types.check(R.id.rb_delivery);
        } **/
    }
}
