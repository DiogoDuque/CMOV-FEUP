package com.cmov.tp1.customer.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class CacheQuery {

    @Query("SELECT * FROM CachedVoucher")
    public abstract List<CachedVoucher> getVouchers();

    @Query("SELECT * FROM CachedVoucher WHERE is_used = :status")
    public abstract List<CachedVoucher> getUnusedVouchers(boolean status);

    @Query("SELECT * FROM CachedTicket")
    public abstract List<CachedTicket> getTickets();


    @Insert
    public abstract void _setVouchers(CachedVoucher... vouchers);

    @Query("DELETE FROM CachedVoucher")
    public abstract void _deleteVouchers();


    @Insert
    public abstract void _setTickets(CachedTicket... tickets);

    @Query("DELETE FROM CachedTicket")
    public abstract void _deleteTickets();

    public void updateTickets(CachedTicket... tickets) {
        _deleteTickets();
        _setTickets(tickets);
    }

    public void updateVouchers(CachedVoucher... vouchers) {
        _deleteVouchers();
        _setVouchers(vouchers);
    }

}
