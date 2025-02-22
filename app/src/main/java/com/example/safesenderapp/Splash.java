package com.example.safesenderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.splashVideoView);

        try {
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
            videoView.setVideoURI(videoUri);

            videoView.setOnCompletionListener(mp -> startLoginActivity());

            videoView.setOnErrorListener((mp, what, extra) -> {
                startLoginActivity();
                return true;
            });

            videoView.start();

        } catch (Exception e) {
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(getApplicationContext(), LogIn.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        VideoView videoView = findViewById(R.id.splashVideoView);
        if (videoView != null) {
            videoView.stopPlayback();
        }
        super.onDestroy();
    }
}
