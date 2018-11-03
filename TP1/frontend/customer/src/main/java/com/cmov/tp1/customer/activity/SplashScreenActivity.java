package com.cmov.tp1.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.core.MyCookieManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MyCookieManager.getInstance(this);

        final Activity activity = this;
        NetworkRequests.checkIfLoggedInRequest(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.i(TAG, "Success -> "+json.toString());
                Intent intent = new Intent(activity.getBaseContext(), ShowsActivity.class);
                activity.getBaseContext().startActivity(intent);
            }

            @Override
            public void onError(JSONObject json) {
                Log.i(TAG, "Error -> "+json.toString());
                try {
                    if(json.getInt("code") == 401) {
                        Intent intent = new Intent(activity.getBaseContext(), LoginActivity.class);
                        activity.getBaseContext().startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
