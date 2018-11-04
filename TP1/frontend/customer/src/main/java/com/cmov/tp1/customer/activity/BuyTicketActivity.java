package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.utility.ToolbarUtility;

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
    }

    private void setShow() {
        TextView nameLabel = findViewById(R.id.show_name_label);
        nameLabel.setText(show.getName());
        TextView dateLabel = findViewById(R.id.show_date_label);
        dateLabel.setText(show.getDate());
        TextView priceLabel = findViewById(R.id.show_price_label);
        priceLabel.setText(Float.toString(show.getPrice()));

        TextView quantityView = findViewById(R.id.quantity_label);
        quantityView.setText("1");
        TextView total = findViewById(R.id.total);
        total.setText(Float.toString(show.getPrice()));
    }

    private void finishPurchase(){
        int showID = show.getId();

        if(quantity <= 0){
            Toast.makeText(this, "Quantity needs to be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO make something here
        return;

        //Intent intent = new Intent(this, ShowsActivity.class);
        //startActivity(intent);
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
}
