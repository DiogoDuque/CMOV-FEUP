package com.cmov.tp1.customer.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HTTPRequestUtility {
    private static final String TAG = "HttpReqUtil";

    public interface OnRequestCompleted{
        void onSuccess(JSONObject json);
        void onError(JSONObject json);
    }

    private static final String HOST = "http://192.168.1.247";
    private static HTTPRequestUtility instance = null;
    private RequestQueue queue;
    private static Context context;

    private HTTPRequestUtility(Context context) {
        this.context = context;
        this.queue = getRequestQueue();
    }

    public static synchronized HTTPRequestUtility getInstance(Context context) {
        if(instance == null)
            instance = new HTTPRequestUtility(context);
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (queue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    public void addToRequestQueue(String path, int method, OnRequestCompleted onRequestCompleted) {
        addToRequestQueue(path, method, null, onRequestCompleted);
    }

    public void addToRequestQueue(String path, int method, JSONObject body, final OnRequestCompleted onRequestCompleted) {
        JsonObjectRequest request = new JsonObjectRequest(method, HOST+path, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Success response: "+response.toString());
                        onRequestCompleted.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject json = new JSONObject();
                try {
                    json.put("message", error.getMessage());
                    if(error.networkResponse != null) {
                        json.put("code", error.networkResponse.statusCode);
                        json.put("data", new String(error.networkResponse.data, "UTF-8"));
                    }
                } catch (JSONException|UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.w(TAG, "Error response: "+json.toString());
                onRequestCompleted.onError(json);
            }
        });

        getRequestQueue().add(request);
    }
}
