package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        CardView card1 = findViewById(R.id.card_view);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(1);
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
        else if(value == 3)
            intent = new Intent(this, TicketsActivity.class);
        else if(value == 4)
            intent = new Intent(this, MyVouchersActivity.class);
        else if(value == 5)
            intent = new Intent(this, MakeOrderActivity.class);
        else if(value == 6)
            intent = new Intent(this, TransactionsActivity.class);
        else if(value == 7)
            getInfoUser();
        else if(value == 8)
            logout();

        startActivity(intent);
    }

    public void logout(){
        NetworkRequests.logoutRequest(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(JSONObject json) {
                Toast.makeText(MainMenuActivity.this, "Impossible to logout", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void getInfoUser(){
        NetworkRequests.getProfileInfo(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json){
                Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
                Bundle b = new Bundle();
                try {
                    b.putInt("id", json.getInt("id"));
                    b.putString("name", json.getString("name"));
                    b.putString("username", json.getString("username"));
                    b.putString("password", json.getString("password"));
                    b.putString("nif", json.getString("nif"));
                    b.putDouble("balance", json.getDouble("balance"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }

            @Override
            public void onError(JSONObject json) {
                Toast.makeText(MainMenuActivity.this, "Error accessing profile", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
