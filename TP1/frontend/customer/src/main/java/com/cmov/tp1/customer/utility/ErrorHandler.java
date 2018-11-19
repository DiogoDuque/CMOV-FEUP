package com.cmov.tp1.customer.utility;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cmov.tp1.customer.activity.LoginActivity;
import com.cmov.tp1.customer.activity.MainMenuActivity;
import com.cmov.tp1.customer.activity.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ErrorHandler {
    public static void genericErrorHandler(Context context, JSONObject json) {
        final String NO_CONN = "java.net.ConnectException: Network is unreachable";
        Integer code = null;
        String message = null;
        try {
            if(json.has("code")) {
                code = json.getInt("code");
            }
            if(json.has("message")) {
                message = json.getString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String errorStr = "Error code: "+(code == null ? "unknown" : code) + "\nMessage: "+(message == null ? json.toString() : message);
        Toast.makeText(context, errorStr, Toast.LENGTH_SHORT).show();
    }
}
