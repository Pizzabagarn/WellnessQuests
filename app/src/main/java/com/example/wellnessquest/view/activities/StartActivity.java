package com.example.wellnessquest.view.activities;

import com.example.wellnessquest.utils.SoundManager;

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

/**
 * StartActivity is the splash screen of the WellnessQuest app.
 * It initializes Firebase, plays background music, starts UI animations,
 * and checks if ML Kit-based tag initialization has been performed.
 *
 * If it is the first launch, it processes quest images to extract visual tags
 * and then navigates to {@link LoginActivity}. If already initialized, it proceeds
 * directly to login after a short delay.
 *
 * @author Alexander Westman
 */
public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    /** Delay (ms) between each character in the typewriter text animation */
    private static final int TYPE_DELAY = 35;

    /** Time (ms) to wait after initialization before transitioning to login */
    private static final int SPLASH_DELAY_AFTER_DONE = 1000;

    /**
     * Called when the activity is created.
     * Initializes Firebase, background music, and either starts tag analysis or transitions to login.
     *
     * @param savedInstanceState the saved state from a previous instance (if any)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        SoundManager.getInstance(this).playBackgroundMusic();

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        binding.logoImage.startAnimation(fade);

        boolean alreadyDone = getSharedPreferences("tag_analysis_prefs", MODE_PRIVATE)
                .getBoolean("mlkit_done", false);

        if (!alreadyDone) {
            binding.statusText.setAlpha(1f);
            typeWriterEffect(binding.statusText, "Setting up WellnessQuest for the first time...", TYPE_DELAY);

            TagInitializer.initializeIfNeeded(this, new TagInitializer.InitCallback() {
                @Override
                public void onDone() {
                    Log.d("TagInit", "Initialization complete");

                    runOnUiThread(() -> {
                        binding.statusText.startAnimation(fade);
                        typeWriterEffect(binding.statusText, "Setup complete! Launching app...", TYPE_DELAY);

                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(StartActivity.this, LoginActivity.class));
                            finish();
                        }, SPLASH_DELAY_AFTER_DONE);
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("TagInit", "Error during tag initialization", e);

                    runOnUiThread(() -> {
                        binding.statusText.setText("Failed to initialize app. Restart it.");
                    });
                }
            });

        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }, 2000);
        }
    }

    /**
     * Displays a typewriter-style animated text reveal in a given TextView.
     *
     * @param textView the TextView to update
     * @param text the full string to display character by character
     * @param delayMs delay in milliseconds between each character
     */
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
