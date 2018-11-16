package com.cmov.tp1.customer.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CafeteriaOrderProduct {

    private String name;
    private double price;
    private int quantity;
    private ArrayList<Integer> voucherIds;

    public CafeteriaOrderProduct(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ArrayList<Integer> getVoucherIds() {
        return voucherIds;
    }

    public boolean addVoucherId(int voucherId) {
        if(voucherIds == null) {
            voucherIds = new ArrayList<>();
        }
        if(voucherIds.size() >= quantity) {
            resetVoucherIds();
            return false;
        }
        voucherIds.add(voucherId);
        return true;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public static String productsToJson(ArrayList<CafeteriaOrderProduct> products) {
        JSONArray array = new JSONArray();
        for(CafeteriaOrderProduct prod: products) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", prod.name);
                obj.put("price", prod.price);
                obj.put("quantity", prod.quantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        return array.toString();
    }

    public void resetVoucherIds() {
        voucherIds = null;
    }

    public static ArrayList<CafeteriaOrderProduct> jsonToProducts(String jsonStr) {
        ArrayList<CafeteriaOrderProduct> products = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                double price = obj.getDouble("price");
                int quantity = obj.getInt("quantity");
                products.add(new CafeteriaOrderProduct(name, price, quantity));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }
}
