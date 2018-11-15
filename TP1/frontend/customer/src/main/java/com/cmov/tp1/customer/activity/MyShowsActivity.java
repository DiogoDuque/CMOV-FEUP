package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.ShowsAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.List;

public class MyShowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shows);

        final RecyclerView recyclerView = findViewById(R.id.unused_tickets_list);

        NetworkRequests.getMyShowsRequest(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Show> showList = ShowsAdapter.parseJsonShows(json);
                ShowsAdapter adapter = new ShowsAdapter(showList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Show show = showList.get(position);
                        Intent intent = new Intent(MyShowsActivity.this, QrGeneratorActivity.class);
                        Toast.makeText(getApplicationContext(), show.getName() + " is selected!", Toast.LENGTH_SHORT).show();
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
