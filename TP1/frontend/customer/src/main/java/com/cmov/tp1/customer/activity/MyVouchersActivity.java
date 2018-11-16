package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Show;
import com.cmov.tp1.customer.core.ShowsAdapter;
import com.cmov.tp1.customer.core.Voucher;
import com.cmov.tp1.customer.core.VouchersAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;

import org.json.JSONObject;

import java.util.List;

public class MyVouchersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vouchers);

        final RecyclerView recyclerView = findViewById(R.id.unused_vouchers);

        NetworkRequests.getMyVouchersByStatus(this, false, new HTTPRequestUtility.OnRequestCompleted() {

            @Override
            public void onSuccess(JSONObject json) {
                final List<Voucher> voucherList = VouchersAdapter.parseJsonVouchers(json);
                VouchersAdapter adapter = new VouchersAdapter(voucherList);
                adapter.setupBoilerplate(getApplicationContext(), recyclerView, new MyClickListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Voucher voucher = voucherList.get(position);
                        Intent intent = new Intent(MyVouchersActivity.this, VoucherPageActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id", voucher.getId());
                        b.putString("product", voucher.getProduct());
                        b.putString("type", voucher.getType());
                        b.putBoolean("isUsed", false);
                        intent.putExtras(b);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                });

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onError(JSONObject json) {
                //TODO handle error
            }
        });
    }
}
