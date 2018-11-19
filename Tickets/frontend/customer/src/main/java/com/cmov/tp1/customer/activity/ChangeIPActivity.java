package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;

public class ChangeIPActivity extends AppCompatActivity {

    String IP = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIP();
            }
        });
    }

    private void saveIP() {
        EditText ip = findViewById(R.id.editText);
        IP = ip.getText().toString();
        HTTPRequestUtility.setHost(IP);
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
    }


}
