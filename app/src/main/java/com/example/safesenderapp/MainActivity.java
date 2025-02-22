package com.example.safesenderapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int selected = 1; // To track the selected fragment
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private static final String TAG = "MainActivity"; // Log tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Activity created");

        // Enable full-screen mode and fit system windows
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        Log.d(TAG, "onCreate: System UI visibility set");

        // Additional flags to handle status bar transparency on some devices
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        Log.d(TAG, "onCreate: Status bar color set to transparent");

        // Apply window insets (e.g., for displaying content under system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), this::onApplyWindowInsets);

        // Initialize views
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.side_drawer);
        toolbar = findViewById(R.id.toolbar);

        // Set up the toolbar as the app bar for the activity
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Log.d(TAG, "onCreate: Toolbar set up");

        // Set up the navigation drawer toggle button (hamburger icon)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        // Set hamburger icon color to black
        toggle.getDrawerArrowDrawable().setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load the default fragment if there is no previously saved state
        if (savedInstanceState == null) {
            replaceFragment(new WalletFragment(), "WalletFragment");
            Log.d(TAG, "onCreate: Default fragment WalletFragment loaded");
        }

        // Listen for changes to the back stack
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
            if (currentFragment instanceof WalletFragment) {
                updateBottomNavigationState(R.id.wallet);
                Log.d(TAG, "onBackStackChanged: WalletFragment is displayed");
            } else if (currentFragment instanceof swapFragment) {
                updateBottomNavigationState(R.id.swap);
                Log.d(TAG, "onBackStackChanged: swapFragment is displayed");
            } else if (currentFragment instanceof notificationsFragment) {
                updateBottomNavigationState(R.id.notifications);
                Log.d(TAG, "onBackStackChanged: notificationsFragment is displayed");
            } else if (currentFragment instanceof profileFragment) {
                updateBottomNavigationState(R.id.profile);
                Log.d(TAG, "onBackStackChanged: profileFragment is displayed");
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Log.d(TAG, "onNavigationItemSelected: Item selected, id: " + itemId);
                if(itemId == R.id.ai) {
                    startActivity(new Intent(MainActivity.this, safesenderChatbot.class));
                    Log.d(TAG, "onNavigationItemSelected: Navigating to AiChatBot activity");
                }
                else if (itemId==R.id.signout) {
                    Intent intent = new Intent(MainActivity.this, LogIn.class);
                    startActivity(intent);
                    finish();



                }
                return true;
            }
        });
    }

    private WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        // Apply system bars insets (e.g., status bar and navigation bar)
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

        Log.d(TAG, "onApplyWindowInsets: Window insets applied");

        // Find and initialize bottom navigation views
        final LinearLayout wallet = findViewById(R.id.wallet);
        final LinearLayout swap = findViewById(R.id.swap);
        final LinearLayout notifications = findViewById(R.id.notifications);
        final LinearLayout profile = findViewById(R.id.profile);

        final ImageView wallet_image = findViewById(R.id.wallet_image);
        final ImageView swap_image = findViewById(R.id.swap_image);
        final ImageView notifications_image = findViewById(R.id.notifications_image);
        final ImageView profile_image = findViewById(R.id.profile_image);

        final TextView wallet_text = findViewById(R.id.wallet_text);
        final TextView swap_text = findViewById(R.id.swap_text);
        final TextView notifications_text = findViewById(R.id.notifications_text);
        final TextView profile_text = findViewById(R.id.profile_text);

        // Set click listeners for bottom navigation tabs
        wallet.setOnClickListener(v1 -> {
            if (selected != 1) {
                Log.d(TAG, "onClick: Wallet tab selected");
                replaceFragment(new WalletFragment(), "WalletFragment");
                updateUI(wallet, wallet_image, wallet_text, R.drawable.wallet_selected);
                selected = 1;
            }
        });

        swap.setOnClickListener(v1 -> {
            if (selected != 2) {
                Log.d(TAG, "onClick: Swap tab selected");
                replaceFragment(new swapFragment(), "SwapFragment");
                updateUI(swap, swap_image, swap_text, R.drawable.swap_selected);
                selected = 2;
            }
        });

        notifications.setOnClickListener(v1 -> {
            if (selected != 3) {
                Log.d(TAG, "onClick: Notifications tab selected");
                replaceFragment(new notificationsFragment(), "NotificationsFragment");
                updateUI(notifications, notifications_image, notifications_text, R.drawable.notifications_selected);
                selected = 3;
            }
        });

        profile.setOnClickListener(v1 -> {
            if (selected != 4) {
                Log.d(TAG, "onClick: Profile tab selected");
                replaceFragment(new profileFragment(), "ProfileFragment");
                updateUI(profile, profile_image, profile_text, R.drawable.profile_selected);
                selected = 4;
            }
        });

        return insets;
    }

    private void replaceFragment(Fragment fragment, String tag) {
        Log.d(TAG, "replaceFragment: Replacing fragment with tag " + tag);
        // Replace the current fragment with the specified fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment, tag)
                .setReorderingAllowed(true)
                .addToBackStack(null) // Allows back navigation
                .commit();
    }

    private void updateUI(LinearLayout selectedLayout, ImageView selectedImage, TextView selectedText, int drawableId) {
        // Reset all UI elements to their default state
        resetUI();

        // Highlight the selected layout
        selectedText.setVisibility(View.VISIBLE);
        selectedImage.setImageResource(drawableId);

        // Change the background resource based on the selected tab
        if (selectedLayout.getId() == R.id.wallet) {
            selectedLayout.setBackgroundResource(R.drawable.round_back_wallet);
        } else if (selectedLayout.getId() == R.id.swap) {
            selectedLayout.setBackgroundResource(R.drawable.round_back_swap);
        } else if (selectedLayout.getId() == R.id.notifications) {
            selectedLayout.setBackgroundResource(R.drawable.round_back_notifications);
        } else if (selectedLayout.getId() == R.id.profile) {
            selectedLayout.setBackgroundResource(R.drawable.round_back_profile);
        }

        // Apply a scale animation to the selected layout
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        selectedLayout.startAnimation(scaleAnimation);

        Log.d(TAG, "updateUI: UI updated for selected tab");
    }

    private void resetUI() {
        // Hide all bottom navigation text views
        findViewById(R.id.wallet_text).setVisibility(View.GONE);
        findViewById(R.id.swap_text).setVisibility(View.GONE);
        findViewById(R.id.notifications_text).setVisibility(View.GONE);
        findViewById(R.id.profile_text).setVisibility(View.GONE);

        // Reset all bottom navigation image views to their default state
        ((ImageView) findViewById(R.id.wallet_image)).setImageResource(R.drawable.wallet);
        ((ImageView) findViewById(R.id.swap_image)).setImageResource(R.drawable.swap);
        ((ImageView) findViewById(R.id.notifications_image)).setImageResource(R.drawable.notifications);
        ((ImageView) findViewById(R.id.profile_image)).setImageResource(R.drawable.profile);

        // Reset all bottom navigation layouts to their default background color
        findViewById(R.id.wallet).setBackgroundColor(ContextCompat.getColor(this, R.color.Tranparent));
        findViewById(R.id.swap).setBackgroundColor(ContextCompat.getColor(this, R.color.Tranparent));
        findViewById(R.id.notifications).setBackgroundColor(ContextCompat.getColor(this, R.color.Tranparent));
        findViewById(R.id.profile).setBackgroundColor(ContextCompat.getColor(this, R.color.Tranparent));

        Log.d(TAG, "resetUI: UI reset to default state");
    }

    private void updateBottomNavigationState(int selectedId) {
        // Change the selected tab based on the fragment currently visible
        LinearLayout selectedLayout = findViewById(selectedId);
        selectedLayout.performClick();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Handling back press");
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragmentContainerView);

        // Handle back press for swapFragment
        if (currentFragment instanceof swapFragment) {
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack(); // Pop the last fragment
                Log.d(TAG, "onBackPressed: Popped back stack for swapFragment");
            } else {
                super.onBackPressed(); // Exit the activity
            }
        } else {
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack(); // Pop the last fragment
                Log.d(TAG, "onBackPressed: Popped back stack for other fragment");
            } else {
                super.onBackPressed(); // Exit the activity
            }
        }
    }
}
