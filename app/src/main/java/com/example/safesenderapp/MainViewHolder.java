package com.example.safesenderapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView txt;
    TextView txt2;
    TextView txt3;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.img);
        txt=itemView.findViewById(R.id.txt);
        txt2=itemView.findViewById(R.id.txt2);
        txt3=itemView.findViewById(R.id.txt3);
    }
}
