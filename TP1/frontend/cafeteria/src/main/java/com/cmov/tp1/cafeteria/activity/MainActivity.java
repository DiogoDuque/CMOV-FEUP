package com.cmov.tp1.cafeteria.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.cmov.tp1.cafeteria.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView card1 = findViewById(R.id.card_view);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(1);
            }
        });

        CardView card2 = findViewById(R.id.card_view2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(2);
            }
        });
    }

    public void setActivity(int value){
        Intent intent = null;
        if(value == 1)
            intent = new Intent(this, ValidateOrderActivity.class);
        else if(value == 2)
            intent = new Intent(this, OrdersActivity.class);

        startActivity(intent);
    }
}
