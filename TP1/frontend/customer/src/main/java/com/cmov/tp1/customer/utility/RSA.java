package com.cmov.tp1.customer.utility;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.security.auth.x500.X500Principal;
import android.util.Base64;
import static java.nio.charset.StandardCharsets.UTF_8;

public class RSA {

    private static final String TAG = "RSA_KEYS";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String RSA_KEY_ALIAS = "myKeys";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String KEY_ALGORITHM = "RSA";
    private static final int keySize = 512;

    public static void buildKeyPair(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, InvalidAlgorithmParameterException, UnrecoverableEntryException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        KeyStore.Entry entry = keyStore.getEntry(RSA_KEY_ALIAS, null);
        if (entry == null) {
            Calendar start = new GregorianCalendar();
            Calendar end = new GregorianCalendar();
            end.add(Calendar.YEAR, 20);
            KeyPairGenerator kgen = KeyPairGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEY_STORE);
            AlgorithmParameterSpec spec = new KeyPairGeneratorSpec.Builder(context)
                    .setKeySize(keySize)
                    .setAlias(RSA_KEY_ALIAS)
                    .setSubject(new X500Principal("CN=" + RSA_KEY_ALIAS))
                    .setSerialNumber(BigInteger.valueOf(12121212))
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();
            kgen.initialize(spec);
            KeyPair kp = kgen.generateKeyPair();
        }
    }

    public static PublicKey getPubKey() {
        try {
            KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(RSA_KEY_ALIAS, null);
            PublicKey pub = ((KeyStore.PrivateKeyEntry)entry).getCertificate().getPublicKey();
            return pub;
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
        return null;
    }

    public static PrivateKey getPrivKey() {
        try {
            KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(RSA_KEY_ALIAS, null);
            PrivateKey priv = ((KeyStore.PrivateKeyEntry)entry).getPrivateKey();
            return priv;
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
        return null;
    }

    public static String sign(String message) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, InvalidKeyException, SignatureException {
        KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
        ks.load(null);
        KeyStore.Entry entry = ks.getEntry(RSA_KEY_ALIAS, null);
        Signature privateSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
        privateSignature.initSign(((KeyStore.PrivateKeyEntry)entry).getPrivateKey());
        privateSignature.update(message.getBytes("UTF-8"));

        byte[] signature = privateSignature.sign();

        return Base64.encodeToString(signature, Base64.NO_WRAP);
    }
}