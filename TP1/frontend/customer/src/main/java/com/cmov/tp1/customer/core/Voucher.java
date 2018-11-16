package com.cmov.tp1.customer.core;

public class Voucher {

    private int id;
    private String type;
    private boolean isUsed;
    private String product;

    public Voucher(int id, boolean isUsed, String product) {
        this.id = id;
        this.type = "Discount";
        this.isUsed = isUsed;
        this.product = product;
    }

    public Voucher(int id, boolean isUsed) {
        this.id = id;
        this.type = "Free Product";
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
}
