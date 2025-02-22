package com.example.safesenderapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class SignUp extends AppCompatActivity {
    ImageView back, divider;
    EditText user, mail, phone, password1, password2;
    TextView signUp;
    Spinner countryCode;
    ProgressBar loadBar;
    FirebaseFirestore db; // Firestore instance
    FirebaseAuth mAuth;   // Firebase Auth instance
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        Log.d(TAG, "onCreate: Activity created");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        divider = findViewById(R.id.line);
        divider.setColorFilter(Color.parseColor("#696969"));

        // Find UI elements
        user = findViewById(R.id.Username);
        mail = findViewById(R.id.email);
        phone = findViewById(R.id.amount1);
        countryCode = findViewById(R.id.countryCode);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        loadBar = findViewById(R.id.bar);
        signUp = findViewById(R.id.register);

        signUp.setOnClickListener(view -> {


            String userName = user.getText().toString().trim();
            String email = mail.getText().toString().trim();
            String Password1 = password1.getText().toString().trim();
            String Password2 = password2.getText().toString().trim();
            String phoneNo = countryCode.getSelectedItem().toString() + phone.getText().toString().trim();

            if (!PassWordCheck(Password1, Password2)) {
                loadBar.setVisibility(View.GONE);
                return;
            }
            if (validateInputs(userName, email, phoneNo, Password1, Password2)) {
                Log.d(TAG, "Inputs validated, proceeding with user registration");
                loadBar.setVisibility(View.VISIBLE);
                registerUser(userName, email, phoneNo, Password1);
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Log.d(TAG, "Back button clicked");
            startActivity(new Intent(getApplicationContext(), LogIn.class));
            finish();
            onBackPressed();
        });
    }

    private boolean PassWordCheck(String pass1, String pass2) {
        if (!pass1.equals(pass2)) {
            password2.setError("Password does not match");
            Log.d(TAG, "PassWordCheck: Passwords do not match");
            return false;
        }
        Log.d(TAG, "PassWordCheck: Passwords match");
        return true;
    }

    private boolean validateInputs(String userName, String email, String phoneNo, String Password1, String Password2) {
        if (userName.isEmpty()) {
            user.setError("Username is required");
            Log.d(TAG, "validateInputs: Username is empty");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email");
            Log.d(TAG, "validateInputs: Invalid email");
            return false;
        }

        if (phoneNo.isEmpty() || phoneNo.length() < 9) {
            phone.setError("Enter a valid phone number");
            Log.d(TAG, "validateInputs: Invalid phone number");
            return false;
        }
        if (Password1.isEmpty() || Password1.length() < 8) {
            password1.setError("Password must be at least 8 characters long");
            Log.d(TAG, "validateInputs: Password is too short");
            return false;
        }
        if (!Password1.equals(Password2)) {
            password2.setError("Passwords do not match");
            Log.d(TAG, "validateInputs: Passwords do not match");
            return false;
        }
        Log.d(TAG, "validateInputs: All inputs are valid");
        return true;
    }

    private void registerUser(String userName, String email, String phoneNo, String Password) {
        mAuth.createUserWithEmailAndPassword(email, Password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String firebaseUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                String customUserId = generateCustomUserId(); // Generate 10-digit unique user ID

                Map<String, Object> userData = new HashMap<>();
                userData.put("username", userName);
                userData.put("email", email);
                userData.put("phone", phoneNo);
                userData.put("firebaseUid", firebaseUid); // Store Firebase UID
                userData.put("customUserId", customUserId); // Store custom user ID

                db.collection("Users").document(firebaseUid).set(userData)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "User data stored successfully");
                            Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LogIn.class));
                            finish();
                            onBackPressed();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Failed to store user data", e);
                            Toast.makeText(SignUp.this, "Failed to store user data: "
                                    + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Log.e(TAG, "Registration failed", task.getException());
                Toast.makeText(SignUp.this, "Registration failed: "
                        + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
            loadBar.setVisibility(View.GONE);
        });
    }

    private String generateCustomUserId() {
        Random random = new Random();
        int tenDigitNumber = 1000000000 + random.nextInt(900000000); // Ensure it's 10 digits
        Log.d(TAG, "Generated custom user ID: " + tenDigitNumber);
        return String.valueOf(tenDigitNumber); // Generate 10-digit random number
    }
}
