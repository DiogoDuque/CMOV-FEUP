package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Ticket;
import com.cmov.tp1.customer.core.TicketsAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketsActivity extends AppCompatActivity {
    static private final String TAG = "TicketsActivity";
    private List<CheckBox> checkboxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Button validate = findViewById(R.id.validate_button);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSelects();
            }
        });

        NetworkRequests.getNotUsedTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Ticket> ticketList = TicketsAdapter.parseJsonTickets(json);
                createCheckBoxes(ticketList);
                TicketsAdapter adapter = new TicketsAdapter(ticketList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Ticket ticket = ticketList.get(position);

                        Intent intent = new Intent(TicketsActivity.this, CardEmulatorActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", ticket.getTicketId());
                        b.putInt("userId", ticket.getUserId());
                        b.putInt("eventId", ticket.getEventId());
                        b.putString("name", ticket.getName());
                        b.putString("date", ticket.getDate());
                        b.putDouble("price", ticket.getPrice());
                        b.putInt("quantity", 1);
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onError(JSONObject json) {
                Log.e(TAG, json.toString()); //TODO handle error
            }
        });

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

    public void createCheckBoxes(List<Ticket> tickets){
        for(int i = 0; i < tickets.size(); i++){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(tickets.get(i).getName() + " - " + tickets.get(i).getDate());
            checkBox.setId(tickets.get(i).getTicketId());
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkboxes.add(checkBox);
        }
    }

    public void changeToSelects(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.INVISIBLE);

        LinearLayout linear1 = (LinearLayout)findViewById(R.id.linear1);
        linear1.setVisibility(View.INVISIBLE);

        LinearLayout linear2 = (LinearLayout)findViewById(R.id.linear2);
        linear2.setVisibility(View.VISIBLE);

        LinearLayout linear3 = (LinearLayout)findViewById(R.id.linear3);
        linear3.setVisibility(View.VISIBLE);

        for(int i = 0; i < checkboxes.size(); i++)
            linear2.addView(checkboxes.get(i));

        
    }
}
