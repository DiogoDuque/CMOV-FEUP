package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.TicketTerminal;

public class CardEmulatorActivity extends AppCompatActivity {
    private TicketTerminal ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        /**Bundle b = getIntent().getExtras();

        if(b.getIntegerArrayList("ticketsID").size() > 0)
            ticket = TicketTerminal.setTicket(b.getInt("userId"), b.getInt("eventId"), b.getIntegerArrayList("ticketsID"), b.getString("name"), b.getString("date"), b.getDouble("price"));

        else
            ticket = TicketTerminal.setTicket(b.getInt("userId"), b.getInt("eventId"), b.getInt("id"), b.getString("name"), b.getString("date"), b.getDouble("price"));

        TextView dateLabel = findViewById(R.id.show_date);
        dateLabel.setText(ticket.getDate());

        TextView priceLabel = findViewById(R.id.show_price);
        priceLabel.setText(Double.toString(ticket.getPrice()));

        TextView showLabel = findViewById(R.id.show_name);
        showLabel.setText(ticket.getName());**/

    }
}
