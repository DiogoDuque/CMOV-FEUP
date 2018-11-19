package com.cmov.tp1.customer.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.TicketTerminal;
import com.cmov.tp1.customer.core.TicketsAdapter;
import com.cmov.tp1.customer.core.db.AppDatabase;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cmov.tp1.customer.utility.ErrorHandler.genericErrorHandler;

public class TicketsActivity extends AppCompatActivity {
    static private final String TAG = "TicketsActivity";
    private List<CheckBox> checkboxes = new ArrayList<>();
    private ArrayList<Integer> ticketsID = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private TicketsAdapter unusedTicketsAdapter;
    private TicketsAdapter selectedTicketsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        final RecyclerView unusedTicketsView = findViewById(R.id.unused_tickets_list);
        final RecyclerView selectedTicketsView = findViewById(R.id.selected_tickets_list);
        final Button finishButton = findViewById(R.id.finish_button);
        final TextView text = findViewById(R.id.textView);

        NetworkRequests.getNotUsedTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<TicketTerminal> unusedTicketList = TicketsAdapter.parseJsonTickets(json);
                unusedTicketsAdapter = new TicketsAdapter(unusedTicketList);
                selectedTicketsAdapter = new TicketsAdapter(new ArrayList<TicketTerminal>());
                final AppDatabase db  = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.query().updateTickets(TicketTerminal.toCachedTickets(unusedTicketList));
                    }
                }).start();

                setupTickets(unusedTicketsView, selectedTicketsView, finishButton, text);
                unusedTicketsView.setAdapter(unusedTicketsAdapter);

                selectedTicketsAdapter.setupBoilerplate(getApplicationContext(), selectedTicketsView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        TicketTerminal t = selectedTicketsAdapter.removeTicket(position);
                        unusedTicketsAdapter.addTicket(t);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                });


            }

            @Override
            public void onError(JSONObject json) {
                try {
                    if(json.getString("message").equals("java.net.ConnectException: Network is unreachable")) {
                        selectedTicketsAdapter = new TicketsAdapter(new ArrayList<TicketTerminal>());
                        final AppDatabase db  = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<TicketTerminal> tickets = TicketTerminal.fromCachedTickets(db.query().getTickets());
                                unusedTicketsAdapter = new TicketsAdapter(tickets);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setupTickets(unusedTicketsView, selectedTicketsView, finishButton, text);
                                        unusedTicketsView.setAdapter(unusedTicketsAdapter);

                                        selectedTicketsAdapter.setupBoilerplate(getApplicationContext(), selectedTicketsView, new MyClickListener.ClickListener() {
                                            @Override
                                            public void onClick(View view, int position) {
                                                TicketTerminal t = selectedTicketsAdapter.removeTicket(position);
                                                unusedTicketsAdapter.addTicket(t);
                                            }

                                            @Override
                                            public void onLongClick(View view, int position) {

                                            }
                                        });
                                    }
                                });
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w(TAG, json.toString());
                genericErrorHandler(getApplicationContext(), json);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTicketsAdapter.getItemCount() == 0) {
                    Toast.makeText(TicketsActivity.this, "You must select ticket to validade.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedTicketsAdapter.getItemCount() > 4) {
                    Toast.makeText(TicketsActivity.this, "No more than 4 tickets may be validated!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<TicketTerminal> tickets = selectedTicketsAdapter.getTickets();
                String date = tickets.get(0).getDate();
                int eventId = tickets.get(0).getEventId();
                for(int i=1; i<selectedTicketsAdapter.getItemCount(); i++){
                    if(!tickets.get(i).getDate().equals(date) || tickets.get(i).getEventId() != eventId){
                        Toast.makeText(TicketsActivity.this, "The tickets' date and show must be the same.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent(TicketsActivity.this, QrGeneratorActivity.class);
                Bundle b = new Bundle();
                b.putString("type", "tickets");
                TicketTerminal ticket = tickets.get(0);
                b.putInt("userId", ticket.getUserId());
                b.putInt("eventId", ticket.getEventId());
                b.putString("name", ticket.getName());
                b.putString("date", ticket.getDate());
                b.putDouble("price", ticket.getPrice());
                if(tickets.size() == 1) {
                    b.putInt("id", ticket.getTicketId());
                    b.putInt("quantity", 1);
                } else {
                    ArrayList<Integer> ticketIds = new ArrayList<>();
                    for(TicketTerminal t: tickets) {
                        ticketIds.add(t.getTicketId());
                    }
                    b.putIntegerArrayList("ticketsID", ticketIds);
                    b.putInt("quantity", tickets.size());
                }
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });

    }

    private void setupTickets(RecyclerView unusedTicketsView, final RecyclerView selectedTicketsView, final Button finishButton, final TextView text) {
        unusedTicketsAdapter.setupBoilerplate(getApplicationContext(), unusedTicketsView, new MyClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectedTicketsView.setVisibility(View.VISIBLE);
                finishButton.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);

                if(selectedTicketsAdapter.getItemCount() >= 4) {
                    Toast.makeText(TicketsActivity.this, "No more than 4 tickets may be validated!", Toast.LENGTH_SHORT).show();
                    return;
                }
                TicketTerminal ticket = unusedTicketsAdapter.removeTicket(position);
                selectedTicketsAdapter.addTicket(ticket);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        });
    }
}
