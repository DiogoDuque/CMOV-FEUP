package com.cmov.tp1.customer.core;

public class Show {
    private int id;
    private String name;
    private String date;
    private float price;

    public Show(int id, String name, String date, float price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }
}
