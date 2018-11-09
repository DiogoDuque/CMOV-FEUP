package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Voucher;
import com.cmov.tp1.customer.core.VouchersAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.ToolbarUtility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VouchersActivity extends AppCompatActivity {

    VouchersAdapter unusedVouchersAdapter;
    VouchersAdapter usingVouchersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        ToolbarUtility.setupToolbar(this);
        ToolbarUtility.setupDrawer(this);

        final RecyclerView unusedVouchers = findViewById(R.id.unused_vouchers);
        final RecyclerView usingVouchers = findViewById(R.id.using_vouchers);

        NetworkRequests.getMyVouchers(this, new HTTPRequestUtility.OnRequestCompleted() {
            @Override
            public void onSuccess(JSONObject json) {
                final List<Voucher> voucherList = VouchersAdapter.parseJsonVouchers(json);
                unusedVouchersAdapter = new VouchersAdapter(voucherList);
                unusedVouchersAdapter.setupBoilerplate(getApplicationContext(), unusedVouchers, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if(usingVouchersAdapter.getVouchersSize() >= 2) {
                            Toast.makeText(VouchersActivity.this,
                                    "Cannot use more than 2 vouchers in a single order!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Voucher voucher = unusedVouchersAdapter.removeVoucher(position);
                        usingVouchersAdapter.addVoucher(voucher);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });
                usingVouchersAdapter = new VouchersAdapter(new ArrayList<Voucher>());
                usingVouchersAdapter.setupBoilerplate(getApplicationContext(), usingVouchers, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Voucher voucher = usingVouchersAdapter.removeVoucher(position);
                        unusedVouchersAdapter.addVoucher(voucher);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                unusedVouchers.setAdapter(unusedVouchersAdapter);
                usingVouchers.setAdapter(usingVouchersAdapter);
            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
