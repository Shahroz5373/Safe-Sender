package com.example.safesenderapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LogIn extends AppCompatActivity {
    ImageView fingerPrint, showPassword;
    RelativeLayout signUp;
    ProgressBar loadBar;
    TextView login;
    EditText password, mail;

    FirebaseAuth mAuth;
    boolean isPasswordVisible = false; // Added boolean for password visibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("LogIn", "onCreate: Activity created");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        loadBar = findViewById(R.id.bar);
        mail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        showPassword = findViewById(R.id.show_pass);
        showPassword.setOnClickListener(view -> {
            Log.d("LogIn", "showPassword clicked");
            if (isPasswordVisible) {
                showPassword.setImageResource(R.drawable.eye_close);
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                showPassword.setImageResource(R.drawable.eye_open);
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            isPasswordVisible = !isPasswordVisible;
            password.setSelection(password.length());
        });

        login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            Log.d("LogIn", "login button clicked");
            String Email = mail.getText().toString().trim();
            String Password = password.getText().toString().trim();
            if (validateInputs(Email, Password)) {
                Log.d("LogIn", "Inputs validated");
                loadBar.setVisibility(View.VISIBLE);
                loginUser(Email, Password);
            } else {
                Log.d("LogIn", "Invalid credentials");
                Toast.makeText(LogIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
            loadBar.setVisibility(View.VISIBLE);
        });

        fingerPrint = findViewById(R.id.finger);
        fingerPrint.setOnClickListener(view -> {
            Log.d("LogIn", "fingerPrint clicked");
            handleBiometricAuthentication();
        });

        signUp = findViewById(R.id.sign_up);
        signUp.setOnClickListener(view -> {
            Log.d("LogIn", "signUp clicked");
            startActivity(new Intent(LogIn.this, SignUp.class));
            finish();
            onBackPressed();
        });


    }

    @SuppressLint("SwitchIntDef")
    public void handleBiometricAuthentication() {
        Log.d("LogIn", "handleBiometricAuthentication called");
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("LogIn", "Biometric authentication available");
                BiometricPrompt biometricPrompt = getPrompt();
                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Please Verify")
                        .setDescription("Authentication is required to proceed")
                        .setNegativeButtonText("Cancel")
                        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                        .build();
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.d("LogIn", "Biometric authentication not supported on this device");
                notifyUser("Biometric authentication not supported on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.d("LogIn", "Biometric authentication is currently unavailable");
                notifyUser("Biometric authentication is currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.d("LogIn", "No biometric credentials are currently enrolled");
                notifyUser("No biometric credentials are currently enrolled.");
                break;
        }
    }

    private BiometricPrompt getPrompt() {
        Log.d("LogIn", "getPrompt called");
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("LogIn", "Biometric authentication error: " + errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d("LogIn", "Biometric authentication succeeded");
                notifyUser("Authentication successful");
                startActivity(new Intent(LogIn.this, MainActivity.class));
                finish();
                onBackPressed();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d("LogIn", "Biometric authentication failed");
                notifyUser("Authentication Failed");
            }
        };
        return new BiometricPrompt(this, executor, callback);
    }

    private void notifyUser(String message) {
        Log.d("LogIn", "notifyUser: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean validateInputs(String email, String Password) {
        Log.d("LogIn", "validateInputs called");
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Enter a valid email");
            return false;
        }
        if (Password.isEmpty() || Password.length() < 8) {
            password.setError("Password must be at least 8 characters long");
            return false;
        }
        return true;
    }

    private void loginUser(String email, String password) {
        Log.d("LogIn", "loginUser called with email: " + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("LogIn", "Login successful");
                        // Login successful, navigate to the main activity
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        onBackPressed();
                    } else {
                        Log.e("LogIn", "Login failed: " + Objects.requireNonNull(task.getException()).getMessage());
                        // Login failed, show error message
                        Toast.makeText(getApplicationContext(), "Authentication failed: " +
                                Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
