package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    }


}
