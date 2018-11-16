package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;

public class ShowPageActivity extends AppCompatActivity {

    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        Bundle b = getIntent().getExtras();
        show = new Show(b.getInt("id"), b.getString("name"), b.getString("date"), b.getFloat("price"));

        setShow();

        Button buyTicketButton = findViewById(R.id.buy_ticket_button);
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBuy();
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
    }

    private void changeToBuy(){
        Intent intent = new Intent(this, BuyTicketActivity.class);

        Bundle b = new Bundle();
        b.putInt("id", show.getId());
        b.putString("name", show.getName());
        b.putString("date", show.getDate());
        b.putFloat("price", show.getPrice());
        intent.putExtras(b); //Put your id to your next Intent

        startActivity(intent);
    }
}
