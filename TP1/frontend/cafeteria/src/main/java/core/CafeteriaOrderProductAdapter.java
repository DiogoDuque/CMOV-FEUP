package core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmov.tp1.cafeteria.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * With help from: https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */
public class CafeteriaOrderProductAdapter extends RecyclerView.Adapter<CafeteriaOrderProductAdapter.MyViewHolder> {

    private RecyclerView view;
    private List<CafeteriaOrderProduct> products;

    public void setupBoilerplate(Context context, RecyclerView recyclerView, MyClickListener.ClickListener clickListener) {
        this.view = recyclerView;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new MyClickListener(context, recyclerView, clickListener));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
        }
    }

    public CafeteriaOrderProductAdapter(List<CafeteriaOrderProduct> products) {
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CafeteriaOrderProduct product = products.get(position);
        holder.name.setText(product.getName());
        holder.date.setText("Quantity: " + product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static List<CafeteriaOrderProduct> parseJsonProducts(JSONObject jsonObject) {
        List<CafeteriaOrderProduct> prodNew = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("orders");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                double price = obj.getDouble("price");
                int quantity = obj.getInt("quantity");
                prodNew.add(new CafeteriaOrderProduct(name, price, quantity));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prodNew;
    }

    public List<CafeteriaOrderProduct> getProducts() {
        return products;
    }
}
