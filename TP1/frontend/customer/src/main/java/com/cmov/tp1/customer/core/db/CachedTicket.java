package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CachedTicket {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "is_used")
    public boolean isUsed;
}
