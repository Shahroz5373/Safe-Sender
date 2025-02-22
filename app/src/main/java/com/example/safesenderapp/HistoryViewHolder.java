package com.example.safesenderapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView date,time,name,id,type,category,sign,amount,currency;
    ImageView image;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        //finds UI items from history_view.xml
        date=itemView.findViewById(R.id.date);
        time=itemView.findViewById(R.id.time);
        name=itemView.findViewById(R.id.client);
        id=itemView.findViewById(R.id.transactionId);
        type=itemView.findViewById(R.id.transactionType);
        category=itemView.findViewById(R.id.category);
        sign=itemView.findViewById(R.id.sign);
        amount=itemView.findViewById(R.id.amount);
        currency=itemView.findViewById(R.id.currency);
        image=itemView.findViewById(R.id.type);
    }
}
