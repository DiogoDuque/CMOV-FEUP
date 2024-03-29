package com.cmov.tp1.customer.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.MyCookieManager;
import com.cmov.tp1.customer.utility.MonthYearPickerDialog;
import com.cmov.tp1.customer.utility.RSA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyCookieManager.getInstance(this);

        Button registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
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

    private void register() throws NoSuchProviderException, NoSuchAlgorithmException {
        EditText nameInput = findViewById(R.id.name_input);
        EditText nifInput = findViewById(R.id.nif_input);
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        EditText cardCodeInput = findViewById(R.id.card_code_input);
        EditText cardNumberInput = findViewById(R.id.card_number_input);
        EditText cardValidityInput = findViewById(R.id.card_validity_input);
        RadioGroup cardTypeGroup = findViewById(R.id.card_type_group);
        RadioButton cardTypeSelected = findViewById(cardTypeGroup.getCheckedRadioButtonId());
        String name = nameInput.getText().toString();

        String nif = nifInput.getText().toString();
        String username = usernameInput.getText().toString();
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

        KeyPair kp = null;
        try {
            kp = RSA.buildKeyPair(this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicKey pubKey = RSA.getPubKey();

        NetworkRequests.registerRequest(this, name, username, nif, password, cardNumber, cardCode,
                cardValidity, cardType, new String(Base64.encode(pubKey.getEncoded(), Base64.DEFAULT)), new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    storeInfoKey(json.getString("uuid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    private void storeInfoKey(String uuid){

        SharedPreferences sPrefs = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString("uuid", uuid);
        editor.apply();
    }

}