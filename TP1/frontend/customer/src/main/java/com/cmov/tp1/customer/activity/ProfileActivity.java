package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmov.tp1.customer.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle b = getIntent().getExtras();
        setValues(b.getString("name"), b.getString("username"), b.getDouble("balance"));


        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    public void setValues(String name, String username, Double balance){
        TextView textName = findViewById(R.id.name_label);
        textName.setText(name);

        TextView textUsername = findViewById(R.id.username_label);
        textUsername.setText(username);

        TextView textBalance = findViewById(R.id.balance);
        textBalance.setText(Double.toString(balance) + "$");
    }

    public void editProfile(){
        Bundle b = getIntent().getExtras();
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
