package com.cmov.tp1.customer.core;

public class Ticket {
    private static int userId;
    private static int eventId;
    private static int ticketId;
    private static String name;
    private static String date;
    private static double price;

    public Ticket(int userId, int eventId, int ticketId, String name, String date, double price) {
        this.userId = userId;
        this.eventId = eventId;
        this.ticketId = ticketId;
        this.name = name;
        this.date = date;
        this.price = price;

    }

    static public Ticket setTicket(int userId2, int eventId2, int ticketId2, String name2, String date2, double price2){
        userId = userId2;
        eventId = eventId2;
        ticketId = ticketId2;
        name = name2;
        date = date2;
        price = price2;

        return new Ticket(userId2, eventId2, ticketId2, name2, date2, price2);
    }

    public int getUserId() {
        return userId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() { return price; }

    static public String getTicket(){
        return Integer.toString(userId) + "/" + Integer.toString(eventId) + "/" + date;
    }
}
