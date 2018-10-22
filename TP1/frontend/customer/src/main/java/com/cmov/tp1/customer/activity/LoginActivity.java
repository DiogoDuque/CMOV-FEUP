package com.cmov.tp1.customer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.AsyncRequest;
import com.cmov.tp1.customer.networking.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

        new LoginRequest(username, password, new AsyncRequest.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(JSONObject json) {
                Iterator<String> keys = json.keys();
                for(; keys.hasNext();) {
                    String key = keys.next();
                    try {
                        Log.d(TAG,key+": "+json.getString(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
