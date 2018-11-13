package core;

public class CafeteriaOrder {
    private int id;
    private String date;
    private double price;

    public CafeteriaOrder(int id, String date, double price) {
        this.id = id;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() { return price; }
}
