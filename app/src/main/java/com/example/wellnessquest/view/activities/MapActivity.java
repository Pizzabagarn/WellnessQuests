package com.example.wellnessquest.view.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private UserViewModel userViewModel;
    private ImageView avatar;
    private final Map<Integer, View> levelTargetMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        avatar = binding.avatar;

        levelTargetMap.put(0, binding.target0);
        levelTargetMap.put(1, binding.target1);
        levelTargetMap.put(2, binding.target2);
        levelTargetMap.put(3, binding.target3);
        levelTargetMap.put(4, binding.target4);
        levelTargetMap.put(5, binding.target5);
        levelTargetMap.put(6, binding.target6);
        levelTargetMap.put(7, binding.target7);
        levelTargetMap.put(8, binding.target8);
        levelTargetMap.put(9, binding.target9);
        levelTargetMap.put(10, binding.target10);

        // ✅ Load user from Firestore using UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);

        // ✅ Observe user and log level
        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                moveAvatarToLevel(user.getCurrentLevel()); //detta måste va kvar, kan inte tas bort, flyttar avatar till aktuell postion vod start
            }
        });

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
            if (!userViewModel.isNextLevel(level)) {
                Toast.makeText(this, "Level locked!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(userViewModel.canPurchaseLevel(level)){
                int cost = userViewModel.getLevelCost(level);
                new AlertDialog.Builder(this)
                        .setMessage("Buy level " + level + " for " + cost + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            userViewModel.purchaseLevel(level);
                            moveAvatarToLevel(level);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void moveAvatarToLevel(int level) {
        View target = levelTargetMap.get(level);
        if (target == null) return;



        // Hämta målpunktskoordinater från nodens placering
        target.post(() -> {
            float targetX = target.getX() - (avatar.getWidth() / 2f);
            float targetY = target.getY()- avatar.getHeight();

            // Animera avataren till nya positionen
            ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(animX, animY);
            set.setDuration(600);
            set.start();
        });
    }
}