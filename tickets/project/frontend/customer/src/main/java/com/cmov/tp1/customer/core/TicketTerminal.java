package com.cmov.tp1.customer.core;

import android.util.Log;

import com.cmov.tp1.customer.core.db.CachedTicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketTerminal {
    private static final String TAG = "TicketTerminal";
    private int userId;
    private int eventId;
    private int ticketId;
    private String name;
    private String date;
    private double price;
    private boolean isMultiple;
    private ArrayList<Integer> ticketsIDs;

    public TicketTerminal(int userId, int eventId, int ticketId, String name, String date, double price) {
        this.userId = userId;
        this.eventId = eventId;
        this.ticketId = ticketId;
        this.name = name;
        this.date = date;
        this.price = price;
        this.isMultiple = false;
    }

    public TicketTerminal(int userId, int eventId, ArrayList tickets, String name, String date, double price) {
        this.userId = userId;
        this.eventId = eventId;
        this.ticketsIDs = tickets;
        this.name = name;
        this.date = date;
        this.price = price;
        this.isMultiple = true;
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

    public boolean getIsMultiple() { return isMultiple; }

    public ArrayList<Integer> getTickets() { return ticketsIDs; }

    public String getTicket(){
        if(!isMultiple)
            return Integer.toString(userId) + "-1-" + Integer.toString(eventId) + "-" + date ;
        else{
            String ticketsInfo = Integer.toString(userId) + "-" + Integer.toString(ticketsIDs.size()) + "-";
            for(int i = 0; i < ticketsIDs.size(); i++){
                ticketsInfo += Integer.toString(ticketsIDs.get(i));
                if(i < (ticketsIDs.size() -1))
                    ticketsInfo += "+";
            }
            ticketsInfo += "-"+Integer.toString(eventId) + "-" + date;
            return ticketsInfo;
        }
    }



    public static CachedTicket[] toCachedTickets(List<TicketTerminal> tickets) {
        List<CachedTicket> cachedTickets = new ArrayList<>();
        for(TicketTerminal t: tickets) {
            if(t.isMultiple) {
                for(int tId: t.ticketsIDs) {
                    CachedTicket ticket = new CachedTicket();
                    ticket.ticketId = tId;
                    ticket.date = t.getDate();
                    ticket.eventId = t.getEventId();
                    ticket.eventName = t.getName();
                    ticket.price = t.getPrice();
                    cachedTickets.add(ticket);
                }
            } else {
                CachedTicket ticket = new CachedTicket();
                ticket.ticketId = t.ticketId;
                ticket.date = t.getDate();
                ticket.eventId = t.getEventId();
                ticket.eventName = t.getName();
                ticket.price = t.getPrice();
                cachedTickets.add(ticket);
            }
        }
        return cachedTickets.toArray(new CachedTicket[cachedTickets.size()]);
    }

    public static List<TicketTerminal> fromCachedTickets(List<CachedTicket> cachedTickets) {
        Log.d(TAG, "Retrieved from cache: "+cachedTickets);
        List<TicketTerminal> tickets = new ArrayList<>();
        for(CachedTicket t: cachedTickets) {
            tickets.add(new TicketTerminal(t.userId, t.eventId, t.ticketId, t.eventName, t.date, t.price));
        }

        Log.d(TAG, "Converted from cache: "+tickets);
        return tickets;
    }

    @Override
    public String toString() {
        return "TicketTerminal{" +
                "userId=" + userId +
                ", eventId=" + eventId +
                ", ticketId=" + ticketId +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", isMultiple=" + isMultiple +
                ", ticketsIDs=" + ticketsIDs +
                '}';
    }
}
