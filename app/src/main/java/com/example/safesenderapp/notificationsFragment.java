package com.example.safesenderapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;  // Import Log class
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class notificationsFragment extends Fragment {

    private static final String TAG = "notificationsFragment"; // Tag for logging

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView rc;

    String[] name = {"Shahroz", "Meesum", "Affaq", "Waleed", "Sameer", "Ali", "Ahmed", "Saeed", "Imran", "Sohail"};
    String[] id = {"0709", "0698", "0712", "0716", "0703", "0714", "0715", "0716", "0717", "0718"};
    String[] category = {"International", "National", "National", "National", "National", "National", "National", "National", "National", "National"};
    String[] type = {"outgoing", "incoming", "outgoing", "incoming", "outgoing", "incoming", "outgoing", "incoming", "incoming", "incoming"};
    Float[] amount = {10000f, 20000f, 30000f, 40000f, 50000f, 60000f, 7000f, 8000f, 9000f, 10000f};
    String[] currency = {"USD", "PKR", "PKR", "PKR", "PKR", "PKR", "PKR", "PKR", "PKR", "PKR"};

    HistoryList historyList;
    public notificationsFragment() {
        // Required empty public constructor
    }

    public static notificationsFragment newInstance(String param1, String param2) {
        notificationsFragment fragment = new notificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d(TAG, "Arguments received: mParam1 = " + mParam1 + ", mParam2 = " + mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_notifications, container, false);

        rc=view.findViewById(R.id.recyclerView);
        historyList=setHistoryList(name, id, category, type, amount, currency);
        if (historyList != null) {
            //recyclerView = findViewById(R.id.recyclerView);
            rc.setLayoutManager(new LinearLayoutManager(getContext()));
            rc.setAdapter(new CustomHistoryAdapter(getContext(), historyList));
        }




        return view;
    }

    public HistoryList setHistoryList(String[] name, String[] id,
                                      String[] category, String[] type,
                                      Float[] amount, String[] currency) {
        // Check if all arrays have the same size
        int size = name.length;
        if (id.length != size || category.length != size || type.length != size ||
                amount.length != size || currency.length != size) {
            Log.e("HistoryList", "Array sizes do not match. Function will stop.");
            return null;
        }

        // Initialize arrays for image and sign
        Integer[] image = new Integer[size];
        String[] sign = new String[size];

        // Populate image and sign arrays based on the type
        for (int i = 0; i < size; i++) {
            if (type[i].equals("incoming")) {
                image[i] = R.drawable.incoming;
                sign[i] = "+";
            } else {
                image[i] = R.drawable.outgoing;
                sign[i] = "-";
            }
        }

        // Initialize lists for date and time
        List<String> date = new ArrayList<>();
        List<String> time = new ArrayList<>();

        // Initialize lists for other fields
        List<String> names = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> signs = new ArrayList<>();
        List<Float> amounts = new ArrayList<>();
        List<String> currencies = new ArrayList<>();
        List<Integer> images = new ArrayList<>();

        // Populate lists using loops
        for (int i = 0; i < size; i++) {
            // Add current date and time for each item
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String timeFormat = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                date.add(dateFormat);
                time.add(timeFormat);
            } else {
                java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                java.text.SimpleDateFormat sdfTime = new java.text.SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                date.add(sdfDate.format(new Date()));
                time.add(sdfTime.format(new Date()));
            }

            // Add other fields
            names.add(name[i]);
            ids.add(id[i]);
            types.add(type[i]);
            categories.add(category[i]);
            signs.add(sign[i]);
            amounts.add(amount[i]);
            currencies.add(currency[i]);
            images.add(image[i]);
        }

        // Create and return a new HistoryList object
        return new HistoryList(date, time, names, ids, types, categories, signs, amounts, currencies, images);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
