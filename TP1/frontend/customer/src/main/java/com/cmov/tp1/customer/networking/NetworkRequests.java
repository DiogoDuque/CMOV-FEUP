package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class NetworkRequests {

    public static void checkIfLoggedInRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/auth";
        final int METHOD = Request.Method.GET;
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void registerRequest(Context context, String name, String username, String nif, String password,
                           String cardNumber, String cardCode, String cardValidity, String cardType,
                           String publicKey, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/auth/register";
        final int METHOD = Request.Method.POST;

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
            body.put("publicKey", publicKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getShowsRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/show/next_shows";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void loginRequest(Context context, String username, String password, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/auth/login";
        final int METHOD = Request.Method.POST;

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
