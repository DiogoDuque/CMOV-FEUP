package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request;
import com.cmov.tp1.customer.networking.core.HTTPRequestUtility;

public class CheckIfLoggedInRequest {

    private static final String PATH = "/auth";
    private static final int METHOD = Request.Method.GET;

    public CheckIfLoggedInRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }
}
