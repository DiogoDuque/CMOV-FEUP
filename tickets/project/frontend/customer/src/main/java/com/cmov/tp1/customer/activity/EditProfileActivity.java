package com.cmov.tp1.customer.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.MonthYearPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle b = getIntent().getExtras();
        setInfo(b);

        Button button = findViewById(R.id.edit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
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

    public void setInfo(Bundle bundle){
        EditText name = findViewById(R.id.name_input);
        name.setText(bundle.getString("name"));

        EditText username = findViewById(R.id.username_input);
        username.setText(bundle.getString("username"));

        EditText nif = findViewById(R.id.nif_input);
        nif.setText(bundle.getString("nif"));
    }

    public void editProfile(){
        EditText nameInput = findViewById(R.id.name_input);
        EditText nifInput = findViewById(R.id.nif_input);
        final EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        EditText cardCodeInput = findViewById(R.id.card_code_input);
        EditText cardNumberInput = findViewById(R.id.card_number_input);
        EditText cardValidityInput = findViewById(R.id.card_validity_input);
        RadioGroup cardTypeGroup = findViewById(R.id.card_type_group);
        RadioButton cardTypeSelected = findViewById(cardTypeGroup.getCheckedRadioButtonId());

        final String name = nameInput.getText().toString();
        String nif = nifInput.getText().toString();
        final String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        final String cardNumber = cardNumberInput.getText().toString();
        final String cardCode = cardCodeInput.getText().toString();
        final String cardValidity = cardValidityInput.getText().toString();
        final String cardType = cardTypeSelected.getText().toString();

        if(password.length() == 0){
            Toast.makeText(this, "Write your password to finalize the edition", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkRequests.updateProfileInfo(this, name, username, nif, password, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                if(cardNumber.length() != 0 && cardValidity.length() != 0 && cardCode.length() != 0){
                    NetworkRequests.updateCreditCardInfo(EditProfileActivity.this, cardType, cardNumber, cardValidity, new HTTPRequestUtility.OnRequestCompleted() {
                        @Override
                        public void onSuccess(JSONObject json) {
                            Toast.makeText(getBaseContext(), "Profile edited successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(JSONObject json) {
                            Toast.makeText(getBaseContext(), json.toString(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                }
            }

            @Override
            public void onError(JSONObject json) {
                Toast.makeText(getBaseContext(), json.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        Intent intent = new Intent(EditProfileActivity.this, MainMenuActivity.class);
        startActivity(intent);
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
