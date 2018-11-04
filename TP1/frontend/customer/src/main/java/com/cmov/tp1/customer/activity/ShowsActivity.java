package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.ShowClickListener;
import com.cmov.tp1.customer.core.ShowsAdapter;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.MyCookieManager;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowsActivity extends AppCompatActivity {
    static private final String TAG = "ShowsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        NetworkRequests.getShowsRequest(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Show> showList = ShowsAdapter.parseJsonShows(json);
                ShowsAdapter adapter = new ShowsAdapter(showList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new ShowClickListener.ClickListener() {
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
}
