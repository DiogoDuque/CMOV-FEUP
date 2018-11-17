package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DBVoucher {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "is_used")
    public boolean isUsed;

    @ColumnInfo(name = "product_name")
    public String productName;

}
