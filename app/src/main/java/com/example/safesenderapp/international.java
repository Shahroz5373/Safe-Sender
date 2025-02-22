package com.example.safesenderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link international#newInstance} factory method to
 * create an instance of this fragment.
 */
public class international extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner spinner_currencies;
    private Spinner spinner_currencies2;
    private CountriesAdapter adapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button bt1;


    public international() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment international.
     */
    // TODO: Rename and change types and number of parameters
    public static international newInstance(String param1, String param2) {
        international fragment = new international();
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
        Log.d("international", "onCreate: Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_international, container, false);
        spinner_currencies = view.findViewById(R.id.csend);
        spinner_currencies2 = view.findViewById(R.id.crec);
        adapter = new CountriesAdapter(getContext(), Data.getCountriesList());
        spinner_currencies.setAdapter(adapter);
        spinner_currencies2.setAdapter(adapter);
        Log.d("international", "onCreateView: View created");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("international", "onDestroyView: View destroyed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("international", "onDestroy: Fragment destroyed");
    }
}
