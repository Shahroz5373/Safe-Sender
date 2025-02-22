package com.example.safesenderapp

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class GoogleQRCodeScanner : AppCompatActivity() {

    private lateinit var scanQRCode: Button
    private lateinit var scanner: GmsBarcodeScanner
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_google_qrcode_scanner)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById(R.id.backButton)
        scanQRCode = findViewById(R.id.scanQRCode)

        backButton.setOnClickListener { finish() }
        setupScanner()

        scanQRCode.setOnClickListener {
            if (isNetworkAvailable()) {
                startScanning()
            } else {
                showToast("No internet connection")
            }
        }
    }

    private fun setupScanner() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build()
        scanner = GmsBarcodeScanning.getClient(this, options)
    }

    private fun startScanning() {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                handleScannedCode(barcode.rawValue)
            }
            .addOnCanceledListener {
                showToast("Scan cancelled")
            }
            .addOnFailureListener { e ->
                showToast("Scan failed: ${e.message}")
            }
    }

    private fun handleScannedCode(rawValue: String?) {
        if (rawValue.isNullOrEmpty()) {
            showToast("Invalid QR code")
            return
        }

        showToast("Scanned QR Code: $rawValue")
        val customUserId = rawValue
        Log.d("QRScanner", "Processing ID: $customUserId")

        firestore.collection("Users").document(customUserId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userId = documentSnapshot.getString("customUserId") ?: "Unknown"
                    val name = documentSnapshot.getString("username") ?: "Unknown"
                    val email = documentSnapshot.getString("email") ?: "No email"
                    val phone = documentSnapshot.getString("phone") ?: "No phone"

                    // Navigate to ReceiverDetails with user data
                    Intent(this, ReceieverDetails::class.java).apply {
                        putExtra("USER_ID", userId)
                        putExtra("NAME", name)
                        putExtra("EMAIL", email)
                        putExtra("PHONE", phone)
                        startActivity(this)
                    }
                    finish()
                } else {
                    showToast("User not found in database")
                }
            }
            .addOnFailureListener { e ->
                showToast("Database error: ${e.message}")
            }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}