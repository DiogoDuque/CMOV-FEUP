package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request.Method;
import com.cmov.tp1.customer.utility.HTTPRequestUtility;

public class GetShowsRequest {

    private static final String PATH = "/show";
    private static final int METHOD = Method.GET;

    public GetShowsRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }


}