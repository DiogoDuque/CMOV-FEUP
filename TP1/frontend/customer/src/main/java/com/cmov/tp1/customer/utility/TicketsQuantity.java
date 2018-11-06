package com.cmov.tp1.customer.utility;

public class TicketsQuantity {
    static private int quantity;

    static public void setQuantity(int quant){
        quantity = quant;
    }

    static public int getQuantity(){
        return quantity;
    }
}
