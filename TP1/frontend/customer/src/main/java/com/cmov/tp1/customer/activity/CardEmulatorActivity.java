package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Ticket;
import com.cmov.tp1.customer.utility.TicketsQuantity;

public class CardEmulatorActivity extends AppCompatActivity {
    private Ticket ticket;
    private TicketsQuantity quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_emulator);

        Bundle b = getIntent().getExtras();
        ticket = Ticket.setTicket(b.getInt("userId"), b.getInt("id"), b.getInt("eventId"), b.getString("name"), b.getString("date"), b.getDouble("price"));

        int quantity = b.getInt("quantity");
        quantities.setQuantity(quantity);

        TextView dateLabel = findViewById(R.id.show_date);
        dateLabel.setText(ticket.getDate());

        TextView priceLabel = findViewById(R.id.show_price);
        priceLabel.setText(Double.toString(ticket.getPrice()));

        TextView showLabel = findViewById(R.id.show_name);
        priceLabel.setText(ticket.getName());
    }
}
