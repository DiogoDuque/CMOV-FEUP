package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request.Method;
import com.cmov.tp1.customer.utility.HTTPRequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequest {
    private static final String TAG = "LoginRequest";

    private static final String PATH = "/auth/login";
    private static final int METHOD = Method.POST;

    public LoginRequest(Context context, String username, String password, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
}
