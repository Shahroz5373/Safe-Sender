package com.example.safesenderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomMainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    Context context;
    MainList mainList;

    public CustomMainAdapter(Context context, MainList mainList) {
        this.context = context;
        this.mainList = mainList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.wallet_countries,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.txt.setText(mainList.getCurrencyName().get(position));
        holder.txt2.setText(mainList.getSymbol().get(position));
        holder.txt3.setText(String.valueOf(mainList.getAmount().get(position)));
        holder.img.setImageResource(mainList.getImage().get(position));

    }

    @Override
    public int getItemCount() {
        return mainList.getAmount().size();
    }
}
