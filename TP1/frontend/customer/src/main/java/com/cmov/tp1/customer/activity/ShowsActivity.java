package com.cmov.tp1.customer.activity;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.core.MyCookieManager;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

public class ShowsActivity extends AppCompatActivity {
    static private final String TAG = "ShowsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        MyCookieManager.getInstance(this);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);
        NetworkRequests.getShowsRequest(this, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                Log.i(TAG, json.toString());
            }

            @Override
            public void onError(JSONObject json) {
                Log.w(TAG, json.toString());
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
