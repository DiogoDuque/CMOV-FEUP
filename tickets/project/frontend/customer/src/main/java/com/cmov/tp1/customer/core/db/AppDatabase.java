package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CachedTicket.class, CachedVoucher.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CacheQuery query();
}
