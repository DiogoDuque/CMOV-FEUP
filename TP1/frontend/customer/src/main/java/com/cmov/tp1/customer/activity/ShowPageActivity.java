package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cmov.tp1.customer.R;

public class ShowPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        addValuesToSpinner();

        Button buyTicketButton = findViewById(R.id.buy_ticket_button);
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBuy();
            }
        });
    }

    private void addValuesToSpinner(){
        //Exemplo
        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner s = (Spinner) findViewById(R.id.show_date_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    private void changeToBuy(){
        Spinner spinner = (Spinner) findViewById(R.id.show_date_spinner);
        int showID = Integer.valueOf(spinner.getSelectedItemPosition());

        Intent intent = new Intent(this, BuyTicketActivity.class);
        startActivity(intent);
    }
}
