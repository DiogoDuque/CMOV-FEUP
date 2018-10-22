package com.cmov.tp1.customer.networking;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequest {
    private static final String TAG = "LoginRequest";

    private static final String PATH = "/auth/login";
    private static final String METHOD = "POST";

    public LoginRequest(String username, String password, AsyncRequest.OnTaskCompleted onTaskCompleted) {
        AsyncRequest request = new AsyncRequest(onTaskCompleted);
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
            request.execute(PATH, METHOD, body.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
