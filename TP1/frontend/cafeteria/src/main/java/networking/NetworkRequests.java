package networking;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class NetworkRequests {

    //VOUCHERS REQUESTS
    public static void getVoucherInfo(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/voucher_info";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateStatus(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/change_voucher";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    //CAFETERIA REQUESTS
    public static void getOrders(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/orders_validated";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getOrderInfo(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/order";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getOrderVouchers(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/order_vouchers";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getOrderProducts(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/order_products";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void verifyOrder(Context context, String message, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/verify";
        final int METHOD = Request.Method.PUT;

        JSONObject body = null;
        try {
            body = new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

}

