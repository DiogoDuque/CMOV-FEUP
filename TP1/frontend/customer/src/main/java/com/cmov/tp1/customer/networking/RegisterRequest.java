package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterRequest {
    private static final String TAG = "LoginRequest";

    private static final String PATH = "/auth/register";
    private static final int METHOD = Request.Method.POST;

    public RegisterRequest(Context context, String name, String username, String nif, String password,
                           String cardNumber, String cardCode, String cardValidity, String cardType,
                           HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        JSONObject body = new JSONObject();
        try {
            body.put("name", name);
            body.put("username", username);
            body.put("nif", nif);
            body.put("password", password);
            body.put("cardNumber", cardNumber);
            body.put("cardCode", cardCode);
            body.put("cardValidity", cardValidity);
            body.put("cardType", cardType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
}
