package com.cmov.tp1.terminal.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class NetworkRequests {

    //TICKETS REQUESTS
    public static void checkTickets(Context context, int userId, int ticketId, String showDate, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("userId", userId);
            body.put("ticketId", ticketId);
            body.put("showDate", showDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
}
