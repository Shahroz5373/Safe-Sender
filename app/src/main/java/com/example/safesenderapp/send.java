package com.example.safesenderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class send extends AppCompatActivity {
    int selected = 0;
    private static final String TAG = "SendActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send);

        Log.d(TAG, "onCreate: Activity created");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            if (selected == 0) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmentContainerView, international.class, null).
                        setReorderingAllowed(true).addToBackStack("name").commit();
                selected = 1;
                Log.d(TAG, "International fragment selected as default");
            }

            Button bt1 = findViewById(R.id.inter);
            Button bt2 = findViewById(R.id.sc);
            ImageView back = findViewById(R.id.backButton);

            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected != 1) {
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.fragmentContainerView, international.class, null).
                                setReorderingAllowed(true).addToBackStack("name").commit();
                        selected = 1;
                        Log.d(TAG, "International fragment selected");
                    }
                }
            });

            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected != 2) {
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.fragmentContainerView, samecurrency.class, null).
                                setReorderingAllowed(true).addToBackStack("name").commit();
                        selected = 2;
                        Log.d(TAG, "SameCurrency fragment selected");
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Back button clicked");
                    Intent intent = new Intent(send.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            return insets;
        });
    }
}
