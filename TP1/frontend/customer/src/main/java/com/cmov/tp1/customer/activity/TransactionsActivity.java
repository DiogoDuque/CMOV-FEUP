package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrder;
import com.cmov.tp1.customer.core.CafeteriaOrderAdapter;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Ticket;
import com.cmov.tp1.customer.core.TicketTerminal;
import com.cmov.tp1.customer.core.TicketsAdapter;
import com.cmov.tp1.customer.core.TicketsAllAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;

import org.json.JSONObject;

import java.util.List;

public class TransactionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        recyclerView1 = findViewById(R.id.list_tickets);
        recyclerView2 = findViewById(R.id.list_orders);

        NetworkRequests.getAllTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Ticket> ticketsList = TicketsAllAdapter.parseJsonTickets(json);
                TicketsAllAdapter adapter = new TicketsAllAdapter(ticketsList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView1, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView1.setAdapter(adapter);
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });


        NetworkRequests.getOrdersValidated(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<CafeteriaOrder> orderList = CafeteriaOrderAdapter.parseJsonOrders(json);
                CafeteriaOrderAdapter adapter = new CafeteriaOrderAdapter(orderList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView2, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        CafeteriaOrder order = orderList.get(position);
                        Intent intent = new Intent(TransactionsActivity.this, QrGeneratorActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("orderId", order.getId());
                        b.putString("date", order.getDate());
                        b.putDouble("price", order.getPrice());
                        intent.putExtras(b);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView2.setAdapter(adapter);
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }
}
