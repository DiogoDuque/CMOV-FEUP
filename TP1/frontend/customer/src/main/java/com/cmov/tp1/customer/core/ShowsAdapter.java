package com.cmov.tp1.customer.core;

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
import android.widget.Toast;

import com.cmov.tp1.customer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * With help from: https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */
public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {

    private List<Show> shows;

    public void setupBoilerplate(Context context, RecyclerView recyclerView, ShowClickListener.ClickListener clickListener) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new ShowClickListener(context, recyclerView, clickListener));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, price;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            date = view.findViewById(R.id.date);
            price = view.findViewById(R.id.price);
        }
    }

    public ShowsAdapter(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Show show = shows.get(position);
        holder.name.setText(show.getName());
        holder.date.setText(show.getDate());
        holder.price.setText(Float.toString(show.getPrice()));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public static List<Show> parseJsonShows(JSONObject jsonObject) {
        List<Show> shows = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("shows");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String date = obj.getString("date"); //TODO parse string correctly
                float price = (float)obj.getDouble("price");
                shows.add(new Show(id, name, date, price));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shows;
    }
}
