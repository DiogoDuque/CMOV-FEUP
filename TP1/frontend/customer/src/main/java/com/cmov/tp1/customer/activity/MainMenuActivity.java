package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.cmov.tp1.customer.R;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        CardView card3 = findViewById(R.id.card_view3);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(3);
            }
        });

        CardView card4 = findViewById(R.id.card_view4);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(4);
            }
        });

        CardView card5 = findViewById(R.id.card_view5);
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(5);
            }
        });

        CardView card6 = findViewById(R.id.card_view6);
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(6);
            }
        });

        CardView card7 = findViewById(R.id.card_view7);
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(7);
            }
        });

        CardView card8 = findViewById(R.id.card_view8);
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(8);
            }
        });
    }

    public void setActivity(int value){
        Intent intent = null;
        if(value == 1)
            intent = new Intent(this, ShowsActivity.class);
        else if(value == 2)
            intent = new Intent(this, MyShowsActivity.class);
        else if(value == 3)
            intent = new Intent(this, TicketsActivity.class);
        else if(value == 4)
            intent = new Intent(this, VouchersActivity.class);
        else if(value == 5)
            intent = new Intent(this, TransactionsActivity.class);
        else if(value == 6)
            intent = new Intent(this, ProfileActivity.class);
        else if(value == 7)
            intent = new Intent(this, TransactionsActivity.class);
        else if(value == 6)

        startActivity(intent);
    }
}
