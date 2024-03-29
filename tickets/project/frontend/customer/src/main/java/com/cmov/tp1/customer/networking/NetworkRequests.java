package com.cmov.tp1.customer.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.Voucher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class NetworkRequests {

    //AUTHENTICATION REQUESTS
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

    public static void logoutRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/auth/logout";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    //SHOWS REQUESTS
    public static void getShowsRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/show/next_shows";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    //PROFILE REQUESTS
    public static void getProfileInfo(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void updateProfileInfo(Context context, String name, String username, String nif, String password, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("name", name);
            body.put("username", username);
            body.put("nif", nif);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateCreditCardInfo(Context context, String card_type, String card_number, String card_validity, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile/credit_card";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("cardType", card_type);
            body.put("cardNumber", card_number);
            body.put("cardValidity", card_validity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    //TICKETS REQUESTS
    public static void getNotUsedTickets(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets/not_used_tickets";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getAllTickets(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets/all_tickets";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void buyTickets(Context context, Show show, int quantity, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("showId", show.getId());
            body.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    //VOUCHERS REQUESTS
    public static void getMyVouchers(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/my_vouchers";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getMyVouchersByStatus(Context context, boolean status, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/my_vouchers_by_status?status="+Boolean.toString(status);
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, null, onRequestCompleted);
    }

    //CAFETERIA REQUESTS
    public static void getOrdersValidated(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/orders_customer_validated";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getOrderProducts(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/order_products?id="+id;
        final int METHOD = Request.Method.GET;
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, null, onRequestCompleted);
    }

    public static void getProductPrice(Context context, String product, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = String.format("/cafeteria/product_price?product=%s", product);
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, null, onRequestCompleted);
    }

    public static void makeOrder(Context context, Date date, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/make_order";
        final int METHOD = Request.Method.POST;
        JSONObject body = new JSONObject();
        try {
            body.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
    
    public static void isOrderValidated(Context context, int orderId, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/is_order_validated?orderId="+orderId;
        final int METHOD = Request.Method.GET;
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, null, onRequestCompleted);
    }
}
