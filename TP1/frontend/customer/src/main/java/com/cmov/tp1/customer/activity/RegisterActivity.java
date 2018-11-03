package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Calendar;
import javax.security.auth.x500.X500Principal;

public class RegisterActivity extends AppCompatActivity {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String KEY_STORE_PROVIDER = "AndroidKeyStore";
    public static final int keySize = 512;

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
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void completeRegister() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException, CertificateException, KeyStoreException, IOException {
        String name = findViewById(R.id.name_input).toString();
        String username = findViewById(R.id.username_input).toString();
        String NIF = findViewById(R.id.nif_input).toString();
        String password = findViewById(R.id.pass_input).toString();

        if(name.length() == 0 || username.length() == 0 || NIF.length() == 0 || password.length() == 0){
            Toast.makeText(this, "Name, username, NIF or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        KeyPair keyPair = generateKeys();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Intent intent = new Intent(this, ShowsActivity.class);
        startActivity(intent);
    }

    private KeyPair generateKeys() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException, KeyStoreException, CertificateException, IOException {
        KeyStore store = KeyStore.getInstance(KEY_STORE_PROVIDER);
        store.load(null);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, KEY_STORE_PROVIDER);
        keyPairGenerator.initialize(keySize);

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 1);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(this)
                .setAlias("RSA_Keys")
                .setKeySize(keySize)
                .setKeyType(KeyProperties.KEY_ALGORITHM_RSA)
                .setEndDate(end.getTime())
                .setStartDate(start.getTime())
                .setSerialNumber(BigInteger.ONE)
                .setSubject(new X500Principal("CN = Secured Preference Store, O = Devliving Online"))
                .build();

        keyPairGenerator.initialize(spec);
        return keyPairGenerator.genKeyPair();
    }

    private void saveUUID(String uuid){
        SharedPreferences sPrefs = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString("uuid", uuid);
        editor.commit();
    }
}
