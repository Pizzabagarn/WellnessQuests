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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.viewmodel.MapViewModel;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private MapViewModel mapViewModel;
    private ActivityMapBinding binding;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        avatar = binding.avatar;
        avatar.setVisibility(View.VISIBLE);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mapViewModel = new MapViewModel(userViewModel);

        binding.setLifecycleOwner(this);
        binding.setUserViewModel(userViewModel);


        // Setup klick för alla noder
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

        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                int currentLevel = user.getCurrentLevel();
                moveAvatarToLevel(currentLevel);
            }
        });
    }

    // Funktion som hanterar när en användare klickar på en nod
    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            int cost = mapViewModel.getCostForLevel(level);

            if (!mapViewModel.canPurchaseLevel(level)) {
                Toast.makeText(this, "You can only buy the next level and must have enough coins.", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Buy Level " + level)
                    .setMessage("Buy level " + level + " for " + cost + " coins?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean purchased = userViewModel.unlockNextLevelIfAffordable();
                        if (purchased) {
                            moveAvatarToLevel(level);
                        } else {
                            Toast.makeText(this, "Purchase failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    public void moveAvatarToLevel(int level) {
        int nodeId = getResources().getIdentifier("node" + level, "id", getPackageName());
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
