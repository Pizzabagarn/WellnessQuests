package com.example.wellnessquest.view.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private UserViewModel userViewModel;
    private TextView textCurrentLevel;
    private Button btnLevelUp;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Level-up knapp
        textCurrentLevel = findViewById(R.id.text_current_level);
        btnLevelUp = findViewById(R.id.btn_level_up);

        avatar = binding.avatar;

        // ✅ Load user from Firestore using UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);

        // ✅ Observe user and log level
        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Log.d("LEVEL_DEBUG", "Loaded level: " + user.getCurrentLevel());
                textCurrentLevel.setText("Level: " + user.getCurrentLevel());
            }
        });

        // ⬆️ Level up when button is clicked
        btnLevelUp.setOnClickListener(v -> userViewModel.levelUpUser());

        // Lägg klicklyssnare med databinding
        setupNodeClick(binding.node0, 0);
        setupNodeClick(binding.node1, 1);
        setupNodeClick(binding.node2, 2);
        setupNodeClick(binding.node3, 3);
        setupNodeClick(binding.node4, 4);
        setupNodeClick(binding.node5, 5);
        setupNodeClick(binding.node6, 6);
        setupNodeClick(binding.node7, 7);
        setupNodeClick(binding.node8, 8);
        setupNodeClick(binding.node9, 9);
        setupNodeClick(binding.node10, 10);
    }

    //flytta avatar och uppdatera level vid nodklick
    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            userViewModel.setUserLevel(level); // ⬅️ uppdatera level till nodens level
            moveAvatarToLevel(level);
        });
    }

    public void moveAvatarToLevel(int level) {
        //TODO: ändra rad 47, getIdentifier
        int nodeId = getResources().getIdentifier("node_" + level, "id", getPackageName());
        TextView target = findViewById(nodeId);
        if (target == null) return;

        // Hämta målpunktskoordinater från nodens placering
        float targetX = target.getX();
        float targetY = target.getY();

        // Animera avataren till nya positionen
        ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.setDuration(600);
        set.start();
    }
}