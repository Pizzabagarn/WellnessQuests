package com.example.wellnessquest.view.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserStorage;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private ActivityMapBinding binding;
    private ImageView avatar;

    private UserViewModel userViewModel;
    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        avatar = binding.avatar;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userStorage = new UserStorage(this);

        // Sätt användaren från local storage
        User currentUser = userStorage.getCurrentUser();
        if (currentUser != null) {
            userViewModel.setUser(currentUser);
        }

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

    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            User currentUser = userViewModel.getUserLiveData().getValue();
            if (currentUser == null) return;

            Level selectedLevel = QuestRepository.getLevel(level);
            int levelCost = selectedLevel.getUnlockCost();

            int currentLevel = currentUser.getCurrentLevel();

            if (level <= currentLevel) {
                moveAvatarToLevel(level); // Redan upplåst
            } else if (currentUser.getCoins() >= levelCost) {
                showPurchaseDialog(level, levelCost);
            } else {
                Toast.makeText(this, "Du har inte tillräckligt med coins för att låsa upp denna nivå.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPurchaseDialog(int level, int cost) {
        new AlertDialog.Builder(this)
                .setTitle("Lås upp nivå " + level)
                .setMessage("Det kostar " + cost + " coins att låsa upp denna nivå. Vill du fortsätta?")
                .setPositiveButton("Köp", (dialog, which) -> {
                    User currentUser = userViewModel.getUserLiveData().getValue();
                    if (currentUser == null) return;

                    // Dra coins
                    currentUser.setCoins(currentUser.getCoins() - cost);

                    // Uppdatera aktuell nivå om den är högre
                    if (level > currentUser.getCurrentLevel()) {
                        currentUser.setCurrentLevel(level);
                    }

                    // Uppdatera och spara
                    userViewModel.setUser(currentUser);
                    userStorage.saveCurrentUser(currentUser);

                    moveAvatarToLevel(level);
                })
                .setNegativeButton("Avbryt", null)
                .show();
    }

    public void moveAvatarToLevel(int level) {
        int nodeId = getResources().getIdentifier("node_" + level, "id", getPackageName());
        TextView target = findViewById(nodeId);
        if (target == null) return;

        float targetX = target.getX();
        float targetY = target.getY();

        ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.setDuration(600);
        set.start();
    }
}
