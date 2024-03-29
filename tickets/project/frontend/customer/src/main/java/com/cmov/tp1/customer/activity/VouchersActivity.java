package com.cmov.tp1.customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.core.CafeteriaOrderProduct;
import com.cmov.tp1.customer.core.MyClickListener;
import com.cmov.tp1.customer.core.Voucher;
import com.cmov.tp1.customer.core.VouchersAdapter;
import com.cmov.tp1.customer.networking.HTTPRequestUtility;
import com.cmov.tp1.customer.networking.NetworkRequests;
import com.cmov.tp1.customer.utility.RSA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import static com.cmov.tp1.customer.utility.ErrorHandler.genericErrorHandler;

public class VouchersActivity extends AppCompatActivity {
    private static final String TAG = "VouchersActivity";


    VouchersAdapter unusedVouchersAdapter;
    VouchersAdapter usingVouchersAdapter;
    private ArrayList<CafeteriaOrderProduct> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> productsStr = bundle.getStringArrayList("strings");
        final int orderId = bundle.getInt("orderID");
        products = CafeteriaOrderProduct.jsonToProducts(bundle.getString("products"));

        final RecyclerView unusedVouchers = findViewById(R.id.unused_vouchers);
        final RecyclerView usingVouchers = findViewById(R.id.using_vouchers);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, productsStr);
        ListView listView = findViewById(R.id.products);
        listView.setAdapter(adapter);

        Button buyButton = findViewById(R.id.cafeteria_buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Voucher> vouchers = usingVouchersAdapter.getVouchers();

                // check if vouchers are valid for the map
                ArrayList<CafeteriaOrderProduct> productsWithVoucher = new ArrayList<>();
                for(Voucher v: vouchers) {
                    if(!v.isFreeProduct()) // if discount, always valid
                        continue;
                    boolean isVoucherValid = false;
                    for(CafeteriaOrderProduct prod: products) {
                        if(prod.getName().equals(v.getProduct()) && prod.addVoucherId(v.getId())) {
                            productsWithVoucher.add(prod);
                            isVoucherValid = true;
                            break;
                        }
                    }
                    if(!isVoucherValid) {
                        // invalid vouchers found, rollback changes on products and return
                        for(CafeteriaOrderProduct prod: productsWithVoucher) {
                            prod.resetVoucherIds();
                        }
                        Toast.makeText(VouchersActivity.this, "One of the chosen vouchers is invalid.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                JSONObject body = getJsonFromVouchersAndProducts(orderId, products, vouchers);
                Log.d(TAG, body.toString());
                Intent intent = new Intent(VouchersActivity.this, QrGeneratorActivity.class);
                Bundle b = new Bundle();
                b.putString("type", "cafeteria");
                b.putString("cafeteriaOrder", body.toString());
                Log.d(TAG, orderId+"");
                b.putInt("orderID", orderId);
                intent.putExtras(b);
                startActivity(intent);

            }
        });

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
                genericErrorHandler(getApplicationContext(), json);
            }
        });
    }

    private JSONObject getJsonFromVouchersAndProducts(int orderId, ArrayList<CafeteriaOrderProduct> products, List<Voucher> vouchers) {
        int userId = getApplicationContext().getSharedPreferences("MyPref", 0).getInt("userId", -1);

        JSONObject obj = new JSONObject();
        try {
            JSONObject order = new JSONObject();
            order.put("orderId", orderId);
            order.put("userId", userId);
            JSONArray jsonProducts = new JSONArray();
            for(CafeteriaOrderProduct prod: products) {
                JSONObject jsonProd = new JSONObject();
                jsonProd.put("name", prod.getName());
                jsonProd.put("quantity", prod.getQuantity());
                jsonProducts.put(jsonProd);
            }
            order.put("products", jsonProducts);
            Log.d(TAG, "finished products: "+order.toString());

            if(vouchers != null && vouchers.size()>0) {
                JSONArray jsonVouchersIds = new JSONArray();
                for(Voucher v: vouchers) {
                    jsonVouchersIds.put(v.getId());
                }
                order.put("vouchersIds", jsonVouchersIds);
            }
            Log.d(TAG, "finished vouchers: "+vouchers);

            obj.put("obj", order);
            String signature = RSA.sign(order.toString());
            obj.put("signature", signature);
        } catch (JSONException | IOException | CertificateException | NoSuchAlgorithmException | InvalidKeyException |
                SignatureException | UnrecoverableEntryException | KeyStoreException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
