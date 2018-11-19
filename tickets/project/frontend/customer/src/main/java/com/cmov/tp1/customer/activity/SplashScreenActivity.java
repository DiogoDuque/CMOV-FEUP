package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.MyCookieManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MyCookieManager.getInstance(this);

        checkIfLoggedIn();
    }

    private void checkIfLoggedIn() {
        NetworkRequests.checkIfLoggedInRequest(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.i(TAG, "Success -> "+json.toString());
                Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                getBaseContext().startActivity(intent);
                finish();
            }

            @Override
            public void onError(JSONObject json) {
                final String WEIRD_ERROR = "java.net.NoRouteToHostException: No route to host";
                final String NO_CONN = "java.net.ConnectException: Network is unreachable";
                try {
                    if(json.has("code") && json.getInt("code") == 401) {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        getBaseContext().startActivity(intent);
                    } else if(json.getString("message").equals(WEIRD_ERROR)) {
                        checkIfLoggedIn();
                    } else if(json.getString("message").equals(NO_CONN)) {
                        int userId = getApplicationContext().getSharedPreferences("MyPref", 0).getInt("userId", -1);
                        Toast.makeText(SplashScreenActivity.this, "Be careful, you're running in offline mode", Toast.LENGTH_SHORT).show();
                        if(userId == -1) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            getBaseContext().startActivity(intent);
                        } else {
                            Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                            getBaseContext().startActivity(intent);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
