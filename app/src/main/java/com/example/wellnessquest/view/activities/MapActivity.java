package com.example.wellnessquest.view.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.viewmodel.MapViewModel;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private ImageView avatar;
    private MapViewModel mapViewModel;
    private UserViewModel userViewModel;
    private QuestRepository questRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sätt upp databinding med layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);

        // Referens till avatar-bilden
        avatar = binding.avatar;

        // Hämta ViewModel instans
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

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

    // Binder en nivå-knapp till klicklogik
    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            int currentLevel = userViewModel.getUserLiveData().getValue().getCurrentLevel();

            if (level != currentLevel + 1) {
                Toast.makeText(this, "You can only buy the next level.", Toast.LENGTH_SHORT).show();
                return;
            }

            int cost = //????
            int coins = userViewModel.getUserLiveData().getValue().getCoins();

            if (coins < cost) {
                Toast.makeText(this, "Not enough coins.", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Buy Level " + level)
                    .setMessage("Buy level " + level + " for " + cost + " coins?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean purchased = userViewModel.unlockNextLevelIfAffordable();
                        if (purchased) moveAvatarToLevel(level);
                        else Toast.makeText(this, "Purchase failed.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    public void moveAvatarToLevel(int level) {
        int nodeId = getResources().getIdentifier("node_" + level, "id", getPackageName());
        TextView target = findViewById(nodeId);
        if (target == null) return;

        target.post(() -> {
            float targetX = target.getX() + target.getWidth() / 2f - avatar.getWidth() / 2f;
            float targetY = target.getY() + target.getHeight() / 2f - avatar.getHeight() / 2f;

            ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(animX, animY);
            set.setDuration(600);
            set.start();
        });
    }
}