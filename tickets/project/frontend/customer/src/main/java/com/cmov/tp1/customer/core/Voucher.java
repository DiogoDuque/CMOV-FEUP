package com.cmov.tp1.customer.core;

import android.util.Log;

import com.cmov.tp1.customer.core.db.CachedVoucher;

import java.util.ArrayList;
import java.util.List;

public class Voucher {
    private static final String TAG = "Voucher";

    private int id;
    private String type;
    private boolean isUsed;
    private String product;

    public Voucher(CachedVoucher voucher) {
        this.id = voucher.id;
        this.type = voucher.type;
        this.isUsed = voucher.isUsed;
        if(type.equals("Free Product")) {
            this.product = voucher.productName;
        }
    }

    public Voucher(int id, boolean isUsed, String product) {
        this.id = id;
        this.type = "Free Product";
        this.isUsed = isUsed;
        this.product = product;
    }

    public Voucher(int id, boolean isUsed) {
        this.id = id;
        this.type = "Discount";
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public String getProduct() {
        return product;
    }

    public boolean isFreeProduct() {
        return product != null;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", isUsed=" + isUsed +
                ", product='" + product + '\'' +
                '}';
    }

    public static List<Voucher> fromCachedVouchers(List<CachedVoucher> cachedVouchers) {
        List<Voucher> vouchers = new ArrayList<>();
        for(CachedVoucher v: cachedVouchers) {
            vouchers.add(new Voucher(v));
        }
        return vouchers;
    }

    public static CachedVoucher[] toCachedVouchers(List<Voucher> vouchers) {
        Log.d(TAG, "to be cached: "+vouchers);
        List<CachedVoucher> cachedVouchers = new ArrayList<>();
        for(Voucher v: vouchers) {
            CachedVoucher voucher = new CachedVoucher();
            voucher.id = v.id;
            voucher.isUsed = v.isUsed;
            voucher.type = v.type;
            voucher.productName = v.product;
            cachedVouchers.add(voucher);
        }
        Log.d(TAG, "sending to cache: "+cachedVouchers);
        return cachedVouchers.toArray(new CachedVoucher[cachedVouchers.size()]);
    }
}
