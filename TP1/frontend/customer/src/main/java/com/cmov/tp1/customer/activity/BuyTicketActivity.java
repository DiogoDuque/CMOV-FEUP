package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyTicketActivity extends AppCompatActivity {

    private Show show;
    private int quantity = 1;
    private float totalToPay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_show_ticket);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
            }
        });

        Bundle b = getIntent().getExtras();
        show = new Show(b.getInt("id"), b.getString("name"), b.getString("date"), b.getFloat("price"));
        setShow();

        Button minusButton = findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(false);
            }
        });

        Button plusButton = findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuantity(true);
            }
        });

        Button finishButton = findViewById(R.id.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishPurchase();
            }
        });
    }

    private void setShow() {
        TextView nameLabel = findViewById(R.id.show_label);
        nameLabel.setText(show.getName());
        TextView dateLabel = findViewById(R.id.show_date);
        dateLabel.setText(show.getDate());
        TextView priceLabel = findViewById(R.id.show_price);
        priceLabel.setText(Float.toString(show.getPrice()));

        TextView quantityView = findViewById(R.id.quantity_label);
        quantityView.setText("1");
        TextView total = findViewById(R.id.total);
        total.setText(Float.toString(show.getPrice()));
    }

    private void finishPurchase(){
        if(quantity <= 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkRequests.buyTickets(this, show, quantity, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Intent intent = new Intent(BuyTicketActivity.this, MyShowsActivity.class);

                try {
                    JSONArray jsonArray = json.getJSONArray("result");
                    StringBuilder sb = new StringBuilder("Received vouchers for: "+jsonArray.getJSONObject(0).getString("product"));
                    for(int i=1; i<jsonArray.length(); i++) {
                        JSONObject ticketJson = jsonArray.getJSONObject(i);
                        sb.append(", ").append(ticketJson.getString("product"));
                        if(i+1==jsonArray.length()) {
                            sb.append(". Your balance is ").append(ticketJson.getDouble("balance")).append(".");
                        }
                    }
                    Toast.makeText(BuyTicketActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }

    private void changeQuantity(boolean type){
        if(type)
            quantity++;
        else {
            if(quantity <=1){
                Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity--;
        }

        totalToPay = quantity * show.getPrice();

        TextView quantityText = (TextView)findViewById(R.id.quantity_label);
        quantityText.setText(Integer.toString(quantity));

        TextView TotalText = (TextView)findViewById(R.id.total);
        TotalText.setText(Float.toString(totalToPay));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
