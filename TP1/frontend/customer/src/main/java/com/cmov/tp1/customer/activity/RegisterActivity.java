package com.cmov.tp1.customer.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.RegisterRequest;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;
import com.cmov.tp1.customer.utility.MonthYearPickerDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

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

        final EditText cardDateInput = findViewById(R.id.card_validity_input);
        cardDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCardDate();
            }
        });
    }

    private void register() {
        EditText nameInput = findViewById(R.id.name_input);
        EditText usernameInput = findViewById(R.id.username_input);
        EditText nifInput = findViewById(R.id.nif_input);
        EditText passwordInput = findViewById(R.id.password_input);
        EditText cardNumberInput = findViewById(R.id.card_number_input);
        EditText cardCodeInput = findViewById(R.id.card_code_input);
        EditText cardValidityInput = findViewById(R.id.card_validity_input);
        RadioGroup cardTypeGroup = findViewById(R.id.card_type_group);
        RadioButton cardTypeSelected = findViewById(cardTypeGroup.getCheckedRadioButtonId());

        String name = nameInput.getText().toString();
        String username = usernameInput.getText().toString();
        String nif = nifInput.getText().toString();
        String password = passwordInput.getText().toString();
        String cardNumber = cardNumberInput.getText().toString();
        String cardCode = cardCodeInput.getText().toString();
        String cardValidity = cardValidityInput.getText().toString();
        String cardType = cardTypeSelected.getText().toString();
        final Activity activity = this; // useful for popping this activity from below callbacks
        if(name.length() == 0 || username.length()==0 || nif.length() != 9 || password.length()==0 ||
                cardNumber.length() != 16 || cardCode.length() != 3 || cardValidity.length()==0 || cardType.length() == 0) {
            Toast.makeText(this, "There are empty/incorrect fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        new RegisterRequest(this, name, username, nif, password, cardNumber, cardCode, cardValidity, cardType, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.i(TAG, "SUCCESSFUL REGISTER -> "+json.toString());
                // TODO KEEP id smwhere
                Toast.makeText(activity.getBaseContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }

            @Override
            public void onError(JSONObject json) {
                Toast.makeText(getBaseContext(), json.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pickCardDate() {
        final EditText cardDateInput = findViewById(R.id.card_validity_input);
        final Calendar calendar = Calendar.getInstance();
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                // update
                cardDateInput.setText(new SimpleDateFormat("MM/yy", Locale.UK).format(calendar.getTime()));
            }
        });
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }
}
