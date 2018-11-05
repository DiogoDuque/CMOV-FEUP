package com.cmov.tp1.customer.utility;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyProperties;
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
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import static com.android.volley.VolleyLog.TAG;

public class RSA {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String RSA_KEY_ALIAS = "RSA_keys";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final int keySize = 512;

    /**public static void main(String [] args) throws Exception {
     // generate public and private keys
     KeyPair keyPair = buildKeyPair();
     PublicKey pubKey = keyPair.getPublic();
     PrivateKey privateKey = keyPair.getPrivate();
     // sign the message
     byte [] signed = encrypt(privateKey, "This is a secret message");
     System.out.println(new String(signed));  // <<signed message>>
     // verify the message
     byte[] verified = decrypt(pubKey, signed);
     System.out.println(new String(verified));     // This is a secret message
     }**/

    public static KeyPair buildKeyPair(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, InvalidAlgorithmParameterException, UnrecoverableEntryException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 25);

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEY_STORE);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(RSA_KEY_ALIAS)
                .setKeySize(keySize)
                .setKeyType(KeyProperties.KEY_ALGORITHM_RSA)
                .setEndDate(end.getTime())
                .setStartDate(start.getTime())
                .setSerialNumber(BigInteger.ONE)
                .setSubject(new X500Principal("CN = Secured Preference Store, O = Devliving Online"))
                .build();

        keyGen.initialize(spec);
        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(SIGNATURE_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(message.getBytes());
    }


    public static byte[] decrypt(PublicKey publicKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(SIGNATURE_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(encrypted);
    }

    public static byte[] sign(byte[] data) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, InvalidKeyException, SignatureException {
        KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
        ks.load(null);
        KeyStore.Entry entry = ks.getEntry(RSA_KEY_ALIAS, null);
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            return null;
        }
        Signature s = Signature.getInstance(SIGNATURE_ALGORITHM);
        s.initSign(((KeyStore.PrivateKeyEntry) entry).getPrivateKey());
        s.update(data);
        return s.sign();
    }

    public static boolean verify(byte[] data, byte[] signature) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
        KeyStore.Entry entry = ks.getEntry(RSA_KEY_ALIAS, null);
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            return false;
        }
        Signature s = Signature.getInstance(SIGNATURE_ALGORITHM);
        s.initVerify(((KeyStore.PrivateKeyEntry) entry).getCertificate());
        s.update(data);
        return s.verify(signature);
    }
}