package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CacheQuery {

    @Query("SELECT * FROM CachedVoucher")
    List<CachedVoucher> getVouchers();

    @Query("SELECT * FROM CachedVoucher WHERE is_used = :status")
    List<CachedVoucher> getUnusedVouchers(boolean status);

    @Query("SELECT * FROM CachedTicket")
    List<CachedVoucher> getTickets();

    @Insert
    void setVouchers(CachedVoucher... vouchers);

}
