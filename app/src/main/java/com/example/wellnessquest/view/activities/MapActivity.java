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
import com.example.wellnessquest.viewmodel.MapViewModel;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private ImageView avatar;
    private MapViewModel mapViewModel;
    private UserViewModel userViewModel;

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
        // Lägg klicklyssnare med databinding (för alla noder/levels)
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
            int cost = mapViewModel.getCostForLevel(level);
            int currentLevel = mapViewModel.getCurrentLevel().getValue();
            int coins = mapViewModel.getCoins().getValue();

            // Kontrollera om spelaren får köpa denna nivå
            if (level != currentLevel + 1) {
                Toast.makeText(this, "You can only buy the next level.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (coins < cost) {
                Toast.makeText(this, "Not enough coins.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Visa bekräftelsedialog
                new AlertDialog.Builder(this)
                        .setTitle("Buy Level " + level)
                        .setMessage("Buy level " + level + "for " + cost + " coins?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            boolean purchased = mapViewModel.purchaseLevel(level);
                            if (purchased) moveAvatarToLevel(level);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
        });
    }

    // Flyttar avataren när man köper ny level
    public void moveAvatarToLevel(int level) {
        //TODO: ändra rad 47, getIdentifier
        int nodeId = getResources().getIdentifier("node_" + level, "id", getPackageName());
        TextView target = findViewById(nodeId);
        if (target == null) return;

        // Vänta tills layouten är färdig för att få korrekta koordinater
        target.post(() -> {
            float targetX = target.getX() + target.getWidth() / 2f - avatar.getWidth() / 2f;
            float targetY = target.getY() + target.getHeight() / 2f - avatar.getHeight() / 2f;

            // Animera avataren till nya positionen
            ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

            // Kör båda samtidigt
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animX, animY);
            set.setDuration(600);
            set.start();
        });
    }
}