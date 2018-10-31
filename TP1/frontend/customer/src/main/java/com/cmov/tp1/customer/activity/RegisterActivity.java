package com.cmov.tp1.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.utility.RSA;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.navigate_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    completeRegister();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void completeRegister() throws NoSuchAlgorithmException {
        String name = findViewById(R.id.name_input).toString();
        String username = findViewById(R.id.username_input).toString();
        String NIF = findViewById(R.id.nif_input).toString();
        String password = findViewById(R.id.pass_input).toString();

        if(name.length() == 0 || username.length() == 0 || NIF.length() == 0 || password.length() == 0){
            Toast.makeText(this, "Name, username, NIF or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        KeyPair keyPair = RSA.buildKeyPair();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        storeInfoKey(privateKey, "uuid");

        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
    }

    private void storeInfoKey(PrivateKey privateKey, String uuid){
        //keystore

        SharedPreferences sPrefs = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString("uuid", uuid);
        editor.commit();
    }
}
