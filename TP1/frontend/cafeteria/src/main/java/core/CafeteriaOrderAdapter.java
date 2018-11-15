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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * With help from: https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */
public class CafeteriaOrderAdapter extends RecyclerView.Adapter<CafeteriaOrderAdapter.MyViewHolder> {

    private RecyclerView view;
    private List<CafeteriaOrder> orders;

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

    public CafeteriaOrderAdapter(List<CafeteriaOrder> orders) {
        this.orders = orders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CafeteriaOrder order = orders.get(position);
        holder.name.setText("Order #" + order.getId());
        holder.date.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static List<CafeteriaOrder> parseJsonOrders(JSONObject jsonObject) {
        List<CafeteriaOrder> ordersNew = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("orders");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String date = reformatDateStr(obj.getString("date"));
                double price = obj.getDouble("price");
                ordersNew.add(new CafeteriaOrder(id, date, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ordersNew;
    }

    private static String reformatDateStr(String oldDate) {
        Pattern pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})\\.000Z");
        Matcher matcher = pattern.matcher(oldDate);
        if(matcher.find()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);
            return String.format("%s/%s/%s %s:%s", day, month, year, hour, minute);
        }
        return "No date parsed";
    }

    public List<CafeteriaOrder> getOrders() {
        return orders;
    }
}