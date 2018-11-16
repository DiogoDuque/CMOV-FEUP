package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);

        Bundle b = getIntent().getExtras();
        setInfo(b);

        Button button = findViewById(R.id.edit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
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

}
