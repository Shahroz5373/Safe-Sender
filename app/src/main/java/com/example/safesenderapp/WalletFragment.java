package com.example.safesenderapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import the Log class
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String data;

    private Spinner spinner_currencies;
    private CountriesAdapter adapter;

    private Button recieve, scan, sendButton;

    private RecyclerView recyclerView;

    private MainList mainList;
    List<Float> amount;

    List<String> symbol;
    List<String> currencyName;
    List<Integer> image;
    Float[] amounts = {110f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f};
    String[] symbols = {"$", "€", "£", " د. إ.", "¥", "Rs", "$", "₽", "₺", " ر.ق", " د.ك", "$", "$", "₩", "$", "R$"};
    String[] currencyNames = {"USD", "EUR", "GBP", "AED", "CNY", "PKR", "AUD", "RUB", "TRY", "QAR", "KWD", "CAD", "ARS", "KRW", "NZD", "BRL"};
    TextView totalAmountTextView;
    TextView totalAmountSign;

    String totalAmountText;
    double totalInUSD;

    private static final double USD_TO_EUR = 1.04;
    private static final double USD_TO_GBP = 1.24;
    private static final double USD_TO_AED = 0.27;
    private static final double USD_TO_CNY = 0.14;
    private static final double USD_TO_PKR = 0.0036;
    private static final double USD_TO_AUD = 0.62;
    private static final double USD_TO_RUB = 0.010;
    private static final double USD_TO_TRY = 0.028;
    private static final double USD_TO_QAR = 0.27;
    private static final double USD_TO_KWD = 3.24;
    private static final double USD_TO_CAD = 0.69;
    private static final double USD_TO_ARS = 0.00095;
    private static final double USD_TO_KRW = 0.00069;
    private static final double USD_TO_NZD = 0.57;
    private static final double USD_TO_BRL = 0.16;

    Integer[] images = {R.drawable.icus, R.drawable.iceuro, R.drawable.icuk, R.drawable.icuae,
            R.drawable.icchina, R.drawable.icpakistan, R.drawable.icaustralia, R.drawable.icrussia, R.drawable.icturkey,
            R.drawable.icqatar, R.drawable.ickuwait, R.drawable.iccanada, R.drawable.icargentina, R.drawable.icsouthkorea, R.drawable.icnewzealand, R.drawable.icbrazil};

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        Log.d("WalletFragment", "onCreate: Fragment created with parameters " + mParam1 + ", " + mParam2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        Log.d("WalletFragment", "onCreateView: View inflated successfully.");

        spinner_currencies = view.findViewById(R.id.Currencies);
        adapter = new CountriesAdapter(getContext(), Data.getCountriesList());
        spinner_currencies.setAdapter(adapter);

        recieve = view.findViewById(R.id.recieve);
        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WalletFragment", "Recieve Button clicked.");
                startActivity(new Intent(getContext(), Recieve.class));
            }
        });

        scan = view.findViewById(R.id.scanQRCode);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WalletFragment", "Scan QR Code Button clicked.");
                startActivity(new Intent(getContext(), GoogleQRCodeScanner.class));
            }
        });

        recyclerView = view.findViewById(R.id.mainRecycler);

        amount = new ArrayList<>(Arrays.asList(amounts));
        symbol = new ArrayList<>(Arrays.asList(symbols));
        currencyName = new ArrayList<>(Arrays.asList(currencyNames));
        image = new ArrayList<>(Arrays.asList(images));

        mainList = new MainList(amount, symbol, currencyName, image);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CustomMainAdapter(getContext(), mainList));
        totalAmountTextView=view.findViewById(R.id.totalAmount);
        totalAmountSign=view.findViewById(R.id.totalAmountsign);

        sendButton = view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("WalletFragment", "Send Button clicked.");
                startActivity(new Intent(getContext(), send.class));
            }
        });

        updateTotalAmount();

        spinner_currencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Countries selectedCountry = (Countries) parentView.getItemAtPosition(position);
                String selectedCurrency = selectedCountry.getName();

                updateTotalAmountS(selectedCurrency);
                updateSymbol(selectedCurrency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("WalletFragment", "No Currency Selected.");
            }
        });





        return view;
    }

    private double convertToUSD(double amount, String currency) {
        switch (currency) {
            case "USD":
                return amount;  // No conversion needed for USD
            case "EUR":
                return amount * USD_TO_EUR;
            case "GBP":
                return amount * USD_TO_GBP;
            case "AED":
                return amount * USD_TO_AED;
            case "CNY":
                return amount * USD_TO_CNY;
            case "PKR":
                return amount * USD_TO_PKR;
            case "AUD":
                return amount * USD_TO_AUD;
            case "RUB":
                return amount * USD_TO_RUB;
            case "TRY":
                return amount * USD_TO_TRY;
            case "QAR":
                return amount * USD_TO_QAR;
            case "KWD":
                return amount * USD_TO_KWD;
            case "CAD":
                return amount * USD_TO_CAD;
            case "ARS":
                return amount * USD_TO_ARS;
            case "KRW":
                return amount * USD_TO_KRW;
            case "NZD":
                return amount * USD_TO_NZD;
            case "BRL":
                return amount * USD_TO_BRL;
            // Add more currencies if needed
            default:
                return 0.0;  // Return 0 if currency is not supported
        }
    }
    private double calculateTotalAmountInUSD() {
        double totalAmountInUSD = 0.0;

        // Assuming `amount` and `currencyName` are lists containing the amounts and their respective currencies
        for (int i = 0; i < amount.size(); i++) {
            double currentAmount = amount.get(i);  // Get amount
            String currentCurrency = currencyName.get(i);  // Get currency name

            // Convert the amount to USD
            double amountInUSD = convertToUSD(currentAmount, currentCurrency);

            // Add the converted amount to the total
            totalAmountInUSD += amountInUSD;
        }

        return totalAmountInUSD;
    }
    private void updateTotalAmount() {
        double totalInUSD = calculateTotalAmountInUSD();
        String totalAmountText = String.format("%.2f", totalInUSD);  // Format to show 2 decimal places
        // Find the TextView
        totalAmountTextView.setText(totalAmountText);  // Set the total amount in the TextView
    }
    private void updateTotalAmountS(String selectedCurrency) {
        double totalInSelectedCurrency = calculateTotalAmountInSelectedCurrency(selectedCurrency);
        String totalAmountText = String.format("%.2f", totalInSelectedCurrency);  // Format to show 2 decimal pla
        totalAmountTextView.setText(totalAmountText);  // Set the total amount in the TextView
    }

    private double convertToCurrency(double amount, String fromCurrency, String toCurrency) {
        // Convert from "fromCurrency" to USD
        double amountInUSD = convertToUSD(amount, fromCurrency);

        // Convert from USD to "toCurrency"
        return convertFromUSD(amountInUSD, toCurrency);
    }

    private double convertFromUSD(double amount, String currency) {
        switch (currency) {
            case "USD":
                return amount;
            case "EUR":
                return amount / USD_TO_EUR;
            case "GBP":
                return amount / USD_TO_GBP;
            case "AED":
                return amount / USD_TO_AED;
            case "CNY":
                return amount / USD_TO_CNY;
            case "PKR":
                return amount / USD_TO_PKR;
            case "AUD":
                return amount / USD_TO_AUD;
            case "RUB":
                return amount / USD_TO_RUB;
            case "TRY":
                return amount / USD_TO_TRY;
            case "QAR":
                return amount / USD_TO_QAR;
            case "KWD":
                return amount / USD_TO_KWD;
            case "CAD":
                return amount / USD_TO_CAD;
            case "ARS":
                return amount / USD_TO_ARS;
            case "KRW":
                return amount / USD_TO_KRW;
            case "NZD":
                return amount / USD_TO_NZD;
            case "BRL":
                return amount / USD_TO_BRL;
            default:
                return 0.0;  // Return 0 if currency is not supported
        }
    }
    private double calculateTotalAmountInSelectedCurrency(String selectedCurrency) {
        double totalAmountInSelectedCurrency = 0.0;

        // Loop through all amounts and convert them to the selected currency
        for (int i = 0; i < amount.size(); i++) {
            double currentAmount = amount.get(i);  // Get amount
            String currentCurrency = currencyName.get(i);  // Get currency name

            // Convert the amount to the selected currency
            double amountInSelectedCurrency = convertToCurrency(currentAmount, currentCurrency, selectedCurrency);

            // Add the converted amount to the total
            totalAmountInSelectedCurrency += amountInSelectedCurrency;
        }

        return totalAmountInSelectedCurrency;
    }
    public void updateSymbol(String selectedCurrency) {
        switch (selectedCurrency) {
            case "USD":
                totalAmountSign.setText("$");
                break;
            case "EUR":
                totalAmountSign.setText("€");
                break;
            case "GBP":
                totalAmountSign.setText("£");
                break;
            case "AED":
                totalAmountSign.setText(" د. إ.");
                break;
            case "CNY":
                totalAmountSign.setText("¥");
                break;
            case "PKR":
                totalAmountSign.setText("Rs");
                break;
            case "AUD":
                totalAmountSign.setText("$");
                break;
            case "RUB":
                totalAmountSign.setText("₽");
                break;
            case "TRY":
                totalAmountSign.setText("₺");
                break;
            case "QAR":
                totalAmountSign.setText(" ر.ق");
                break;
            case "KWD":
                totalAmountSign.setText(" د.ك");
                break;
            case "CAD":
                totalAmountSign.setText("$");
                break;
            case "ARS":
                totalAmountSign.setText("$");
                break;
            case "KRW":
                totalAmountSign.setText("₩");
                break;
            case "NZD":
                totalAmountSign.setText("$");
                break;
            case "BRL":
                totalAmountSign.setText("R$");
                break;
        }




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
