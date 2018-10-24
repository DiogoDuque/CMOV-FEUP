package com.cmov.tp1.customer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.LoginRequest;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        EditText userInput = findViewById(R.id.user_input);
        EditText passInput = findViewById(R.id.pass_input);
        String username = userInput.getText().toString();
        String password = passInput.getText().toString();
        if(username.length()==0 || password.length()==0) {
            Toast.makeText(this, "Username or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        new LoginRequest(this, username, password, new HTTPRequestUtility.OnRequestCompleted() {
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
}
