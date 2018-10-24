package com.cmov.tp1.customer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmov.tp1.customer.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        EditText nameInput = findViewById(R.id.user_input);
        EditText usernameInput = findViewById(R.id.username_input);
        EditText nifInput = findViewById(R.id.nif_input);
        EditText passwordInput = findViewById(R.id.password_input);
        String name = nameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String nif = nifInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(name.length() == 0 || username.length()==0 || nif.length() != 9 || password.length()==0) {
            Toast.makeText(this, "There are empty fields yet.", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO credit card stuff
    }
}
