package com.example.wellnessquest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityStartBinding;
import com.google.firebase.FirebaseApp;

public class StartActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // 2 sekunder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 🔥 Initiera Firebase
        FirebaseApp.initializeApp(this);

        ActivityStartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        binding.logoImage.post(() -> {
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            binding.logoImage.startAnimation(fadeIn);
        });

        new Handler().postDelayed(() -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}
