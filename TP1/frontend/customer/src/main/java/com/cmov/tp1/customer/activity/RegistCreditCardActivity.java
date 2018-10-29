package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

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
                changeToRegister();
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
        String number = findViewById(R.id.number_input).toString();
        String csc = findViewById(R.id.csc_input).toString();
        String date = findViewById(R.id.date_input).toString();

        if(number.length() == 0 || csc.length() == 0 || date.length() == 0){
            Toast.makeText(this, "Credit card number, csc ou date is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId()) {
            case R.id.mastercard:
                break;
            case R.id.visa:
                break;
            default:
                Toast.makeText(this, "You need to select a credit card type", Toast.LENGTH_SHORT).show();
                return;
        }

        changeToRegister();
    }

    private void changeToRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
