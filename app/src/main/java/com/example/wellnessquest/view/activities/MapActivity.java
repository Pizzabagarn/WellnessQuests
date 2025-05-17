package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private UserViewModel userViewModel;
    private TextView textCurrentLevel;
    private Button btnLevelUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        textCurrentLevel = findViewById(R.id.text_current_level);
        btnLevelUp = findViewById(R.id.btn_level_up);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);

        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Log.d("LEVEL_DEBUG", "Loaded level: " + user.getCurrentLevel());
                textCurrentLevel.setText("Level: " + user.getCurrentLevel());
            }
        });
        btnLevelUp.setOnClickListener(v -> userViewModel.levelUpUser());

    }
}