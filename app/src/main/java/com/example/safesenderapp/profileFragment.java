package com.example.safesenderapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log; // Import Logcat for logging

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {

    private TextView username, uid, email, phone, nameProfile;
    Button verify;
    TextView verification;
    ImageView verifyImage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Log.d("profileFragment", "onCreate() - FirebaseAuth and FirebaseFirestore initialized.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = view.findViewById(R.id.username);
        uid = view.findViewById(R.id.uid);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        nameProfile = view.findViewById(R.id.nameProfile);
        verify=view.findViewById(R.id.verifyButton);
        verification=view.findViewById(R.id.verification);
        verifyImage=view.findViewById(R.id.verifyImage);

        ImageView copyButton = view.findViewById(R.id.copy);

        copyButton.setOnClickListener(view1 -> copyAccountDetailsToClipboard());

        Log.d("profileFragment", "onCreateView() - View inflated, setting up views.");

        fetchUserDataFromFirebase();

        return view;
    }

    private void fetchUserDataFromFirebase() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            Log.d("profileFragment", "User logged in, UID: " + userId);

            db.collection("Users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Populate TextViews with user data
                            String fetchedUsername = documentSnapshot.getString("username");
                            String fetchedUid = documentSnapshot.getString("customUserId");
                            String fetchedEmail = documentSnapshot.getString("email");
                            String fetchedPhone = documentSnapshot.getString("phone");

                            username.setText(fetchedUsername != null ? fetchedUsername : "No data");
                            nameProfile.setText(fetchedUsername != null ? fetchedUsername : "No data");
                            uid.setText(fetchedUid != null ? fetchedUid : "No data");
                            email.setText(fetchedEmail != null ? fetchedEmail : "No data");
                            phone.setText(fetchedPhone != null ? fetchedPhone : "No data");

                            Log.d("profileFragment", "User data fetched successfully: " + fetchedUsername);
                        } else {
                            // Set "No data" if the document doesn't exist
                            setNoData();
                            Log.d("profileFragment", "No user data found for UID: " + userId);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error fetching user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("profileFragment", "Error fetching user data: " + e.getMessage(), e);
                        setNoData();
                    });
        } else {
            // No user is logged in
            setNoData();
            Log.d("profileFragment", "No user is logged in.");
        }
    }

    private void setNoData() {
        username.setText("No data");
        uid.setText("No data");
        email.setText("No data");
        phone.setText("No data");
        nameProfile.setText("No data");
        
        verify.setVisibility(View.VISIBLE);
        verification.setText("Not Verified");
        verifyImage.setVisibility(View.GONE);


        Log.d("profileFragment", "No data to display for user.");
    }

    private void copyAccountDetailsToClipboard() {
        String accountDetails = "Username: " + username.getText().toString() + "\n" +
                "UID: " + uid.getText().toString() + "\n" +
                "Email: " + email.getText().toString() + "\n" +
                "Phone: " + phone.getText().toString();

        ClipboardManager clipboard = (ClipboardManager) ContextCompat.getSystemService(requireContext().getApplicationContext(), ClipboardManager.class);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("Account Details", accountDetails);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Account details copied to clipboard!", Toast.LENGTH_SHORT).show();
            Log.d("profileFragment", "Account details copied to clipboard: " + accountDetails);
        } else {
            Toast.makeText(getContext(), "Failed to access clipboard", Toast.LENGTH_SHORT).show();
            Log.e("profileFragment", "Failed to access clipboard.");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("profileFragment", "onDestroyView() - Fragment view destroyed.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("profileFragment", "onDestroy() - Fragment destroyed.");
    }
}
