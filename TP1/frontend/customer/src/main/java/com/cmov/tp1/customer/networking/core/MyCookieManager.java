package com.cmov.tp1.customer.networking.core;

import android.content.Context;
import android.util.Log;

import java.net.CookieHandler;
import java.net.CookiePolicy;

public class MyCookieManager {
    static private String TAG = "MyCookieManager";
    static private MyCookieManager instance;

    private MyCookieManager(Context context) {
        Log.i(TAG, "Starting my cookie manager...");
        SiCookieStore2 siCookieStore = new SiCookieStore2(context);
        java.net.CookieManager cookieManager = new java.net.CookieManager(siCookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    public static MyCookieManager getInstance(Context context) {
        if(instance == null)
            instance = new MyCookieManager(context);
        return instance;
    }
}
