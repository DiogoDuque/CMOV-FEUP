package com.cmov.tp1.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.MyCookieManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyCookieManager.getInstance(this);

        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button registerBtn = findViewById(R.id.navigate_register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void login() {
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(username.length()==0 || password.length()==0) {
            Toast.makeText(this, "Username or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final Activity activity = this;
        NetworkRequests.loginRequest(this, username, password, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.i(TAG, json.toString());
                try {
                    if(json.getBoolean("result")) {
                        Intent intent = new Intent(activity.getBaseContext(), ShowsActivity.class);
                        activity.getBaseContext().startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject json) {
                Log.w(TAG, json.toString());
            }
        });
    }
}
