package com.cmov.tp1.terminal.networking;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public abstract class NetworkRequests {

    //TICKETS REQUESTS
    public static void checkTickets(Context context, int ticketId, String showDate, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/";
        final int METHOD = Request.Method.GET;


        JSONObject body = new JSONObject();
        try {
            body.put("ticketId", ticketId);
            body.put("showDatw", showDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
}
