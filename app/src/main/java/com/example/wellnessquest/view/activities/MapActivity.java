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
import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.viewmodel.MapViewModel;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private ImageView avatar;
    private UserViewModel userViewModel;
    private MapViewModel mapViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        avatar = binding.avatar;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        for (int i = 0; i <= 10; i++) {
            int finalI = i;
            View node = findViewById(getResources().getIdentifier("node" + finalI, "id", getPackageName()));
            if (node != null) setupNodeClick(node, finalI);
        }
    }

    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            Level levelObj = mapViewModel.getLevel(level);
            int cost = levelObj.getUnlockCost();
            int currentLevel = userViewModel.getUserLiveData().getValue().getCurrentLevel();
            int coins = userViewModel.getUserLiveData().getValue().getCoins();

            if (level != currentLevel + 1) {
                Toast.makeText(this, "You can only buy the next level.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (coins < cost) {
                Toast.makeText(this, "Not enough coins.", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Buy Level " + level)
                    .setMessage("Buy level " + level + " for " + cost + " coins?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean purchased = userViewModel.purchaseLevel(level);
                        if (purchased) moveAvatarToLevel(level);
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
