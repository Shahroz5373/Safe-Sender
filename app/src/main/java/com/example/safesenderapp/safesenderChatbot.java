package com.example.safesenderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class safesenderChatbot extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendButton;
    ImageView back;
    private LinearLayout chatContainer;
    private RequestQueue requestQueue;
    private ScrollView sv;

    // Replace with your Google Cloud API key (keep it secure)
    private static final String API_KEY = "Use Your Own Api Key Thanks";
    private static final String MODEL_ID = "gemini-1.5-flash";
    private static final String API_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_ID + ":generateContent?key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_safesender_chatbot);

        Log.d("safesenderChatbot", "onCreate: Activity created");

        // Set up views
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_button);
        chatContainer = findViewById(R.id.chat_container);
        requestQueue = Volley.newRequestQueue(this);
        sv = findViewById(R.id.scrollView);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("safesenderChatbot", "back button clicked");
                Intent intent = new Intent(safesenderChatbot.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Apply window insets (for edge-to-edge layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.safeChatbot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the send button listener
        sendButton.setOnClickListener(v -> {
            String userMessage = messageEditText.getText().toString().trim();
            messageEditText.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null && getCurrentFocus() != null) {
                // Hide the keyboard from the current focused view
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            if (userMessage.isEmpty()) {
                Toast.makeText(safesenderChatbot.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("safesenderChatbot", "User message: " + userMessage);

            // Display user message
            displayMessage(userMessage, R.drawable.user_message_container);

            // Create API request body
            try {
                JSONObject part = new JSONObject();
                part.put("text", userMessage);

                JSONObject content = new JSONObject();
                content.put("parts", new JSONArray().put(part));

                JSONObject requestBody = new JSONObject();
                requestBody.put("contents", new JSONArray().put(content));

                sendMessageToAPI(requestBody);
            } catch (JSONException e) {
                Log.e("safesenderChatbot", "Error creating request body", e);
                Toast.makeText(safesenderChatbot.this, "Error creating request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Send message to API and get response
    private void sendMessageToAPI(JSONObject requestBody) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_ENDPOINT,
                requestBody,
                response -> {
                    Log.d("API_RESPONSE", response.toString());
                    try {
                        JSONArray candidates = response.getJSONArray("candidates");
                        JSONObject firstCandidate = candidates.getJSONObject(0);
                        JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
                        String aiResponse = parts.getJSONObject(0).getString("text");

                        runOnUiThread(() -> displayMessage(aiResponse, R.drawable.ai_reply_container));
                    } catch (JSONException e) {
                        Log.e("safesenderChatbot", "Error parsing response", e);
                        Toast.makeText(safesenderChatbot.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        Log.e("API_ERROR", "Response Code: " + error.networkResponse.statusCode);
                        Log.e("API_ERROR", "Response Data: " + new String(error.networkResponse.data));
                    }
                    Toast.makeText(safesenderChatbot.this, "Network/API error", Toast.LENGTH_SHORT).show();
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,  // Timeout in ms
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.d("safesenderChatbot", "Sending API request");

        requestQueue.add(jsonObjectRequest);
    }

    // Display messages in chat
    private void displayMessage(String message, int backgroundResource) {
        LinearLayout messageLayout = new LinearLayout(this);
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setBackgroundResource(backgroundResource);
        messageLayout.setPadding(16, 16, 16, 16);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 30, 0, 0);
        messageLayout.setLayoutParams(layoutParams);

        TextView messageTextView = new TextView(this);
        messageTextView.setText(message);
        messageTextView.setTextSize(18);
        messageTextView.setTextColor(getResources().getColor(android.R.color.black));

        messageLayout.addView(messageTextView);
        chatContainer.addView(messageLayout);

        Log.d("safesenderChatbot", "Displayed message: " + message);

        sv.post(() -> sv.fullScroll(View.FOCUS_DOWN));
    }
}
