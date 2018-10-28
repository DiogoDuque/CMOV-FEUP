package com.cmov.tp1.customer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cmov.tp1.customer.R;

public class RegistCreditCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_creditcard);

        Button backButton = findViewById(R.id.minus_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_register);
            }
        });

        Button associateButton = findViewById(R.id.minus_button);
        associateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associateCreditCard();
            }
        });
    }

    private void associateCreditCard(){
        setContentView(R.layout.activity_register);
    }
}
