package com.example.safesenderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CountriesAdapter2 extends BaseAdapter {
    private Context context;
    private List<Countries> countries;
    public CountriesAdapter2(Context context, List<Countries> countries)
    {
        this.context=context;
        this.countries=countries;
    }
    @Override
    public int getCount() {

        return countries != null ? countries.size():0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).
                inflate(R.layout.items_countries2, parent ,false);

        TextView text=view.findViewById(R.id.name);
        ImageView img=view.findViewById(R.id.image);

        text.setText(countries.get(position).getName());
        img.setImageResource(countries.get(position).getImage());




        return view;
    }
}
