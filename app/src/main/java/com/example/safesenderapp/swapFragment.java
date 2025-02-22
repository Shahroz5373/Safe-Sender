package com.example.safesenderapp;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link swapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class swapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner_currencies;
    private Spinner spinner_currencies2;
    private CountriesAdapter2 adapter;
    private static final String TAG = "swapFragment";

    Layout item_countries;

    public swapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment swapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static swapFragment newInstance(String param1, String param2) {
        swapFragment fragment = new swapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d(TAG, "onCreate: Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_swap, container, false);
        spinner_currencies = view.findViewById(R.id.spinner1);
        spinner_currencies2 = view.findViewById(R.id.spinner2);
        Log.d(TAG, "onCreateView: View created");

        // Load data asynchronously
        loadDataAsync();

        return view;
    }

    private void loadDataAsync() {
        Log.d(TAG, "loadDataAsync: Loading data asynchronously");
        // ExecutorService to load data in the background thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Simulate data fetching or replace with your actual method to fetch the list
                final List<Countries> countries = Data.getCountriesList(); // Replace with real data loading logic
                Log.d(TAG, "run: Data fetched");

                // Post the result to the main thread to update the UI (spinners)
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Set adapter once the data is loaded
                        adapter = new CountriesAdapter2(getContext(), countries);
                        spinner_currencies.setAdapter(adapter);
                        spinner_currencies2.setAdapter(adapter);
                        Log.d(TAG, "run: UI updated with data");
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: View destroyed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Fragment destroyed");
    }
}
