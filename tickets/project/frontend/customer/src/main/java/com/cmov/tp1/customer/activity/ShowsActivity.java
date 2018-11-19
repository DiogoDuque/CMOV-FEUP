package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.ShowsAdapter;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;

import org.json.JSONObject;

import java.util.List;

import static com.cmov.tp1.customer.utility.ErrorHandler.genericErrorHandler;

public class ShowsActivity extends AppCompatActivity {
    static private final String TAG = "ShowsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        final RecyclerView recyclerView = findViewById(R.id.unused_tickets_list);

        NetworkRequests.getShowsRequest(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Show> showList = ShowsAdapter.parseJsonShows(json);
                ShowsAdapter adapter = new ShowsAdapter(showList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Show show = showList.get(position);

                        Intent intent = new Intent(ShowsActivity.this, ShowPageActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", show.getId());
                        b.putString("name", show.getName());
                        b.putString("date", show.getDate());
                        b.putFloat("price", show.getPrice());
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
                genericErrorHandler(getApplicationContext(), json);
            }
        });
    }
}
