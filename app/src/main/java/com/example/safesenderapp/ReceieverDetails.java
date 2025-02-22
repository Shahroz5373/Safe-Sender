package com.example.safesenderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReceieverDetails extends AppCompatActivity {
    EditText username, userid, email, phone, accountNo, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reciever_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("ReceieverDetails", "onCreate: Activity created");

        username = findViewById(R.id.Username);
        userid = findViewById(R.id.Userid);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        accountNo = findViewById(R.id.accountNo);
        country = findViewById(R.id.country);

        // Extracting data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            Log.d("ReceieverDetails", "onCreate: Intent received");
            String userId = intent.getStringExtra("USER_ID");
            String name = intent.getStringExtra("NAME");
            String userEmail = intent.getStringExtra("EMAIL");
            String userPhone = intent.getStringExtra("PHONE");

            // Setting data to EditText fields
            Log.d("ReceieverDetails", "onCreate: Setting data to EditText fields");
            username.setText(name);
            userid.setText(userId);
            email.setText(userEmail);
            phone.setText(userPhone);
        } else {
            Log.d("ReceieverDetails", "onCreate: No intent data received");
        }
    }
}
