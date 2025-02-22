package com.example.safesenderapp

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class Recieve : AppCompatActivity() {

    private lateinit var qrCode: ImageView
    private lateinit var auth: FirebaseAuth
    private var qrBitmap: Bitmap? = null
    private val TAG = "RecieveActivity"
    private val QR_SIZE = 1024
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recieve)

        Log.d(TAG, "onCreate: Activity created")
        backButton = findViewById(R.id.backButton)
        qrCode = findViewById(R.id.qrCode)

        backButton.setOnClickListener {
            finish()
        }

        auth = FirebaseAuth.getInstance()
        generateQrFromFirebaseId()
    }

    private fun generateQrFromFirebaseId() {
        val user = auth.currentUser
        val userId = user?.uid

        if (userId.isNullOrEmpty()) {
            Log.e(TAG, "User not logged in")
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Firebase User ID: $userId")
        generateQrCode(userId)
    }

    private fun generateQrCode(firebaseUserId: String) {
        try {
            val encoder = BarcodeEncoder()
            qrBitmap = encoder.encodeBitmap(firebaseUserId, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE)

            runOnUiThread {
                if (qrBitmap != null) {
                    qrCode.setImageBitmap(qrBitmap)
                    Log.d(TAG, "QR Code generated and set successfully")
                } else {
                    Log.e(TAG, "QR Bitmap is null")
                    Toast.makeText(this, "Failed to generate QR Code", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: WriterException) {
            Log.e(TAG, "Error generating QR Code: ${e.message}")
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show()
        }
    }
}