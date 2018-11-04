package com.cmov.tp1.customer.networking;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

    //SHOWS REQUESTS
    public static void getShowsRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/show/next_shows";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getMyShowsRequest(Context context, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/show/my_shows";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    //PROFILE REQUESTS
    public static void getProfileInfo(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getProfileID(Context context, String username, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile/user_id";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getCreditCard(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile/credit_card";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateProfileInfo(Context context, int id, String name, String username, String nif, String password, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("name", name);
            body.put("username", username);
            body.put("nif", nif);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateCreditCardInfo(Context context, int id, String card_type, String card_number, Date card_validity, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile/credit_card";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("card_type", card_type);
            body.put("card_number", card_number);
            body.put("card_validity", card_validity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateBalance(Context context, int id, double adding, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/profile/credit_card";
        final int METHOD = Request.Method.PUT;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("adding", adding);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    //TICKETS REQUESTS
    public static void getTicket(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getUsedTickets(Context context, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets/used_tickets";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("customer_id", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getNotUsedTickets(Context context, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets/not_used_tickets";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("customer_id", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getAllTickets(Context context, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets/all_tickets";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("customer_id", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void buyTicket(Context context, int costumer_id, int show, String place, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/tickets";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("user", costumer_id);
            body.put("show", show);
            body.put("place", place);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    //VOUCHERS REQUESTS
    public static void getMyVouchers(Context context, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/my_vouchers";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("customer_id", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getMyVouchersByStatus(Context context, int costumer_id, boolean status, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/my_vouchers_by_status";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("customer_id", costumer_id);
            body.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getVoucherInfo(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/voucher_info";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void createVoucher(Context context, int ticket, String type, int product, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/create_voucher";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("ticket", ticket);
            body.put("type", type);
            body.put("product", product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void updateStatus(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/vouchers/change_voucher";
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
    public static void getOrders(Context context, boolean date, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/orders";
        final int METHOD = Request.Method.GET;

        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, onRequestCompleted);
    }

    public static void getOrdersCostumer(Context context, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/orders_costumer";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void getOrderInfo(Context context, int id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/order";
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
        final String PATH = "/cafeteria/order_products";
        final int METHOD = Request.Method.GET;

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void makeOrder(Context context, Date date, int costumer_id, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/make_order";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("date", date);
            body.put("customer", costumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void addProductToOrder(Context context, int order, int product, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/add_products";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("order", order);
            body.put("product", product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }

    public static void addProductToOrder(Context context, int order, int product, int voucher, HTTPRequestUtility.OnRequestCompleted onRequestCompleted) {
        final String PATH = "/cafeteria/add_products";
        final int METHOD = Request.Method.POST;

        JSONObject body = new JSONObject();
        try {
            body.put("order", order);
            body.put("product", product);
            body.put("voucher", voucher);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HTTPRequestUtility.getInstance(context).addToRequestQueue(PATH, METHOD, body, onRequestCompleted);
    }
}
