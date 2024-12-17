package com.example.projectmad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.splashVideoView);
        // Ensure video is rendered correctly on all devices
        videoView.setZOrderOnTop(true);

        // Set the path to the video in the raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.new_logo);
        videoView.setVideoURI(videoUri);

        // Start video playback
        videoView.setOnCompletionListener(mediaPlayer -> {
            // After video finishes, navigate to the main activity
            Intent intent = new Intent(splash.this, start.class);
            startActivity(intent);
            finish(); // Close the splash activity
        });
        getSupportActionBar().hide();
        videoView.start();
    }
}