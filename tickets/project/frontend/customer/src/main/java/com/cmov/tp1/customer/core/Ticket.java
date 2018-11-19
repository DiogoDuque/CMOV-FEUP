package com.cmov.tp1.customer.core;


import com.cmov.tp1.customer.core.db.CachedTicket;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int eventId;
    private int ticketId;
    private String name;
    private String date;
    private double price;
    private boolean isUsed;

    public Ticket(int eventId, int ticketId, String name, String date, double price, boolean isUsed) {

        this.eventId = eventId;
        this.ticketId = ticketId;
        this.name = name;
        this.date = date;
        this.price = price;
        this.isUsed = isUsed;
    }

    public int getEventId() {
        return eventId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public boolean isUsed() {
        return isUsed;
    }



    public static CachedTicket[] toCachedTickets(List<Ticket> tickets) {
        List<CachedTicket> cachedTickets = new ArrayList<>();
        for(Ticket t: tickets) {

            CachedTicket ticket = new CachedTicket();
            ticket.ticketId = t.getTicketId();
            ticket.date = t.getDate();
            ticket.eventId = t.getEventId();
            ticket.eventName = t.getName();
            ticket.price = t.getPrice();
            cachedTickets.add(ticket);
        }
        return cachedTickets.toArray(new CachedTicket[cachedTickets.size()]);
    }
}
