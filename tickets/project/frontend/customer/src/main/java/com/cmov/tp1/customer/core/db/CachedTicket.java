package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CachedTicket {
    @PrimaryKey
    public int ticketId;

    @ColumnInfo
    public int eventId;

    @ColumnInfo
    public int userId;

    @ColumnInfo
    public String date;

    @ColumnInfo
    public double price;

    @ColumnInfo(name = "name")
    public String eventName;

    @Override
    public String toString() {
        return "CachedTicket{" +
                "ticketId=" + ticketId +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
