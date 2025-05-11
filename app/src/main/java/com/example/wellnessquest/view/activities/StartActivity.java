package com.example.wellnessquest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityStartBinding;
import com.example.wellnessquest.utils.TagInitializer;
import com.google.firebase.FirebaseApp;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    // Skrivhastighet för typwriter-effekt (millisekunder per tecken)
    private static final int TYPE_DELAY = 35;

    // Hur länge appen väntar innan den startar nästa aktivitet efter initiering
    private static final int SPLASH_DELAY_AFTER_DONE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔥 Initiera Firebase
        FirebaseApp.initializeApp(this);

        // 🧷 Koppla layouten med databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        // 🌀 Starta fade-in animation för loggan
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        binding.logoImage.startAnimation(fade);

        // 💾 Kontrollera om taggar redan har analyserats en gång
        boolean alreadyDone = getSharedPreferences("tag_analysis_prefs", MODE_PRIVATE)
                .getBoolean("mlkit_done", false);

        if (!alreadyDone) {
            // 🟡 Första uppstarten – visa status och kör initiering
            binding.statusText.setAlpha(1f);
            typeWriterEffect(binding.statusText, "Setting up WellnessQuest for the first time...", TYPE_DELAY);

            TagInitializer.initializeIfNeeded(this, new TagInitializer.InitCallback() {
                @Override
                public void onDone() {
                    Log.d("TagInit", "🚀 Klar med initiering!");

                    // ❗️All uppdatering av UI måste ske på huvudtråden (main thread)
                    runOnUiThread(() -> {
                        binding.statusText.startAnimation(fade);
                        typeWriterEffect(binding.statusText, "Setup complete! Launching app...", TYPE_DELAY);

                        // Vänta lite innan vi går vidare till inloggningssidan
                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(StartActivity.this, LoginActivity.class));
                            finish();
                        }, SPLASH_DELAY_AFTER_DONE);
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("TagInit", "💥 Fel vid tag-init", e);

                    // ❗️Måste också göras på UI-tråd
                    runOnUiThread(() -> {
                        binding.statusText.setText("Failed to initialize app. Restart it.");
                    });
                }
            });

        } else {
            // ✅ Om appen redan har initierats – gå direkt till login
            new Handler().postDelayed(() -> {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }, 2000);
        }
    }

    // 🧠 Typwriter-effekt som skriver text i ett TextView tecken för tecken
    private void typeWriterEffect(TextView textView, String text, long delayMs) {
        textView.setText("");
        final Handler handler = new Handler();
        for (int i = 0; i <= text.length(); i++) {
            int finalI = i;
            handler.postDelayed(() -> {
                textView.setText(text.substring(0, finalI));
            }, i * delayMs);
        }
    }
}
