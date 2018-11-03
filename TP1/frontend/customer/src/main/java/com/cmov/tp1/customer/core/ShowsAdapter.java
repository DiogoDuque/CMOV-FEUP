package com.cmov.tp1.customer.core;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmov.tp1.customer.R;

import java.util.List;

/**
 * With help from: https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */
public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {

    private List<Show> shows;

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

}
