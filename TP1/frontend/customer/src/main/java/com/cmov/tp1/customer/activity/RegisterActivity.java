package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmov.tp1.customer.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registButton = findViewById(R.id.navigate_register_button);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRegister();
            }
        });
    }

    private void completeRegister(){
        String name = findViewById(R.id.name_input).toString();
        String username = findViewById(R.id.username_input).toString();
        String NIF = findViewById(R.id.nif_input).toString();
        String password = findViewById(R.id.pass_input).toString();

        if(name.length() == 0 || username.length() == 0 || NIF.length() == 0 || password.length() == 0){
            Toast.makeText(this, "Name, username, NIF or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
    }
}
