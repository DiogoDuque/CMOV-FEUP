package com.cmov.tp1.customer.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmov.tp1.customer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.MyViewHolder> {

    private static final String TAG = "VouchersAdapter";
    private List<Voucher> vouchers;
    private RecyclerView view;

    public void setupBoilerplate(Context context, RecyclerView recyclerView, MyClickListener.ClickListener clickListener) {
        view = recyclerView;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new MyClickListener(context, recyclerView, clickListener));
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type, isUsed;

        public MyViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.type);
            isUsed = view.findViewById(R.id.isUsed);
        }
    }

    public VouchersAdapter(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public VouchersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voucher_list_row, parent, false);

        return new VouchersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VouchersAdapter.MyViewHolder holder, int position) {
        Voucher voucher = vouchers.get(position);
        String type = voucher.isFreeProduct() ? ("Free "+voucher.getProduct()) : "5% discount";
        holder.type.setText(type);
        holder.isUsed.setText(voucher.isUsed() ? "Used" : "Not used");
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public static List<Voucher> parseJsonVouchers(JSONObject jsonObject) {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("vouchers");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String type = obj.getString("type");
                boolean isUsed = Boolean.parseBoolean(obj.getString("is_used"));
                if(type.equals("Free Product")) {
                    vouchers.add(new Voucher(id, isUsed, obj.getString("product")));
                } else {
                    vouchers.add(new Voucher(id, isUsed));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    public Voucher removeVoucher(int index) {
        Voucher v = vouchers.remove(index);
        view.setAdapter(this);
        return v;
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
        view.setAdapter(this);
    }

    public Voucher getVoucherAtPosition(int index) {
        return vouchers.get(index);
    }

    public int getVouchersSize() {
        return vouchers.size();
    }
}
