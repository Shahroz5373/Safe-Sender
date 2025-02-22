package com.example.safesenderapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomHistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    Context context;
    HistoryList historyList;

    public CustomHistoryAdapter(Context context, HistoryList historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        return new HistoryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.history_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        // Ensure the position is valid
        if (position < 0 || position >= getItemCount()) {
            Log.e("CustomHistoryAdapter", "Invalid position: " + position);
            return;
        }

        // Bind data to the ViewHolder
        try {
            holder.date.setText(historyList.getDate().get(position));
            holder.time.setText(historyList.getTime().get(position));
            holder.name.setText(historyList.getName().get(position));
            holder.id.setText(historyList.getId().get(position));
            holder.type.setText(historyList.getType().get(position));
            holder.category.setText(historyList.getCategory().get(position));
            holder.sign.setText(historyList.getSign().get(position));
            holder.amount.setText(String.valueOf(historyList.getAmount().get(position)));
            holder.currency.setText(historyList.getCurrency().get(position));
            holder.image.setImageResource(historyList.getImage().get(position));
        } catch (IndexOutOfBoundsException e) {
            Log.e("CustomHistoryAdapter", "IndexOutOfBoundsException at position: " + position, e);
        }
    }

    @Override
    public int getItemCount() {

        return Math.min(
                historyList.getId().size(),
                Math.min(
                        historyList.getDate().size(),
                        Math.min(
                                historyList.getTime().size(),
                                Math.min(
                                        historyList.getName().size(),
                                        Math.min(
                                                historyList.getType().size(),
                                                Math.min(
                                                        historyList.getCategory().size(),
                                                        Math.min(
                                                                historyList.getSign().size(),
                                                                Math.min(
                                                                        historyList.getAmount().size(),
                                                                        Math.min(
                                                                                historyList.getCurrency().size(),
                                                                                historyList.getImage().size()
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
}