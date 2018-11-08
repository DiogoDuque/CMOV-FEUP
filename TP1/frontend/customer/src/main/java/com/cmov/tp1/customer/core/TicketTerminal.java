package com.cmov.tp1.customer.core;

import java.util.ArrayList;

public class TicketTerminal {
    private static int userId;
    private static int eventId;
    private static int ticketId;
    private static String name;
    private static String date;
    private static double price;
    private static boolean isMultiple;
    private static ArrayList<Integer> ticketsIDs;

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

    static public TicketTerminal setTicket(int userId2, int eventId2, int ticketId2, String name2, String date2, double price2){
        userId = userId2;
        eventId = eventId2;
        ticketId = ticketId2;
        name = name2;
        date = date2;
        price = price2;

        return new TicketTerminal(userId2, eventId2, ticketId2, name2, date2, price2);
    }

    static public TicketTerminal setTicket(int userId2, int eventId2, ArrayList<Integer> tickets, String name2, String date2, double price2){
        userId = userId2;
        eventId = eventId2;
        ticketsIDs = tickets;
        name = name2;
        date = date2;
        price = price2;

        return new TicketTerminal(userId2, eventId2, tickets, name2, date2, price2);
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

    public static String getTicket(){
        if(!isMultiple)
            return Integer.toString(userId) + "/1/" + Integer.toString(eventId) + "/" + date ;
        else{
            String ticketsInfo = Integer.toString(userId) + "/" + Integer.toString(ticketsIDs.size()) + "/";
            for(int i = 0; i < ticketsIDs.size(); i++){
                ticketsInfo += Integer.toString(ticketsIDs.get(i));
                if(i < (ticketsIDs.size() -1))
                    ticketsInfo += "-";
            }
            ticketsInfo += Integer.toString(eventId) + "/" + date;
            return ticketsInfo;
        }
    }
}
