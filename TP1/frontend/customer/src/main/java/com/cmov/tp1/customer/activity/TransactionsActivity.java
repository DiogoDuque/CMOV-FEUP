package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrder;
import com.cmov.tp1.customer.core.CafeteriaOrderAdapter;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.ShowsAdapter;
import com.cmov.tp1.customer.core.TicketTerminal;
import com.cmov.tp1.customer.core.TicketsAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.List;

public class TransactionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        addValuesToSpinner();

        NetworkRequests.getAllTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<CafeteriaOrder> orderList = CafeteriaOrderAdapter.parseJsonOrders(json);
                CafeteriaOrderAdapter adapter = new CafeteriaOrderAdapter(orderList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        CafeteriaOrder order = orderList.get(position);
                        Intent intent = new Intent(TransactionsActivity.this, QrGeneratorActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("orderId", order.getId());
                        b.putString("date", order.getDate());
                        b.putDouble("price", order.getPrice());
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.type_transaction);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTransactions();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Spinner spinner2 = (Spinner)findViewById(R.id.type_result);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getTransactions();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
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

    private void addValuesToSpinner(){
        Spinner spinner = findViewById(R.id.type_transaction);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.type_result);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.result, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
    }

    private void getTransactions(){
        Spinner spinner = findViewById(R.id.type_transaction);
        Spinner spinner2 = findViewById(R.id.type_result);

        if(spinner.getSelectedItem().toString().equals("Cafeteria Order") && spinner.getSelectedItem().toString().equals("New")){
            NetworkRequests.getOrdersNotValidated(this, new HTTPRequestUtility.OnRequestCompleted() {

                @Override
                public void onSuccess(JSONObject json) {
                    final List<CafeteriaOrder> orderList = CafeteriaOrderAdapter.parseJsonOrders(json);
                    CafeteriaOrderAdapter adapter = new CafeteriaOrderAdapter(orderList);
                    adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            CafeteriaOrder order = orderList.get(position);
                            Intent intent = new Intent(TransactionsActivity.this, QrGeneratorActivity.class);
                            Bundle b = new Bundle();
                            b.putInt("orderId", order.getId());
                            b.putString("date", order.getDate());
                            b.putDouble("price", order.getPrice());
                            intent.putExtras(b); //Put your id to your next Intent
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onError(JSONObject json) {
                    //TODO handle error
                }
            });
        }
        else if(spinner.getSelectedItem().toString().equals("Cafeteria Order") && spinner.getSelectedItem().toString().equals("Validated")){
            NetworkRequests.getOrdersValidated(this, new HTTPRequestUtility.OnRequestCompleted() {

                @Override
                public void onSuccess(JSONObject json) {
                    final List<CafeteriaOrder> orderList = CafeteriaOrderAdapter.parseJsonOrders(json);
                    CafeteriaOrderAdapter adapter = new CafeteriaOrderAdapter(orderList);
                    adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            CafeteriaOrder order = orderList.get(position);
                            Intent intent = new Intent(TransactionsActivity.this, QrGeneratorActivity.class);
                            Bundle b = new Bundle();
                            b.putInt("orderId", order.getId());
                            b.putString("date", order.getDate());
                            b.putDouble("price", order.getPrice());
                            intent.putExtras(b); //Put your id to your next Intent
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onError(JSONObject json) {
                    //TODO handle error
                }
            });
        }
        else if(spinner.getSelectedItem().toString().equals("Tickets") && spinner.getSelectedItem().toString().equals("New")){
            NetworkRequests.getNotUsedTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

                @Override
                public void onSuccess(JSONObject json) {
                    final List<TicketTerminal> ticketsList = TicketsAdapter.parseJsonTickets(json);
                    TicketsAdapter adapter = new TicketsAdapter(ticketsList);
                    adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onError(JSONObject json) {
                    //TODO handle error
                }
            });
        }
        else if(spinner.getSelectedItem().toString().equals("Tickets") && spinner.getSelectedItem().toString().equals("Validated")){
            NetworkRequests.getUsedTickets(this, new HTTPRequestUtility.OnRequestCompleted() {

                @Override
                public void onSuccess(JSONObject json) {
                    final List<TicketTerminal> ticketsList = TicketsAdapter.parseJsonTickets(json);
                    TicketsAdapter adapter = new TicketsAdapter(ticketsList);
                    adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }

                        @Override
                        public void onLongClick(View view, int position) {
                        }
                    });

                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onError(JSONObject json) {
                    //TODO handle error
                }
            });
        }
    }
}
