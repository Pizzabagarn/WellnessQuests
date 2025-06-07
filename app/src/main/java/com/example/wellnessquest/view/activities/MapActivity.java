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
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
/**
 * MapActivity displays a game-like map with levels that users can unlock using coins.
 * Each level is represented by a node on the map. The user can move their avatar
 * by unlocking and selecting levels. Data is loaded from Firebase via a UserViewModel.
 *
 * This activity uses data binding and observes changes to user data in real time.
 * It includes level purchase confirmation dialogs, sound effects, and avatar animations.
 * @author Lowisa Svensson Christell
 */

public class MapActivity extends BaseDrawerActivity {
    private static final String TAG = "MapActivity";
    private ActivityMapBinding binding;
    private UserViewModel userViewModel;
    private ImageView avatar;
    private final Map<Integer, View> levelTargetMap = new HashMap<>();

    /**
     * Called when the activity is starting. Initializes the view binding, loads user data,
     * sets up map nodes, and connects click listeners to nodes for level interaction.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        drawerBinding.contentFrame.addView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        avatar = binding.avatar;

        // Map each level to its corresponding target view on the map
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

        // Load user from Firestore using UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);

        // Observe user and log level
        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                moveAvatarToLevel(user.getCurrentLevel()); //detta måste va kvar, kan inte tas bort, flyttar avatar till aktuell postion vod start
            }
        });

        // Set click listeners on each map node
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

    /**
     * Sets up a click listener on a map node. When clicked, checks if the level is unlocked,
     * allows purchasing if enough coins are available, and moves the avatar.
     * @param view  The map node view.
     * @param level The level associated with the node.
     */
    private void setupNodeClick(View view, int level) {
        view.setOnClickListener(v -> {
            if (!userViewModel.isNextLevel(level)) {
                SoundManager.getInstance(MapActivity.this).playError();
                Toast.makeText(this, "Level locked!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(userViewModel.canPurchaseLevel(level)){
                int cost = userViewModel.getLevelCost(level);
                new AlertDialog.Builder(this)
                        .setMessage("Buy level " + level + " for " + cost + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            SoundManager.getInstance(MapActivity.this).playLevelUp();
                            userViewModel.purchaseLevel(level);
                            moveAvatarToLevel(level);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(this, "Not enough coins!", Toast.LENGTH_SHORT).show();
                SoundManager.getInstance(MapActivity.this).playError();
            }
        });
    }
    /**
     * Animates the avatar's movement to the specified level's target view on the map.
     * If the target view or avatar is missing, logs an error.
     * @param level The level to which the avatar should move.
     */
    public void moveAvatarToLevel(int level) {
        View target = levelTargetMap.get(level);
        if (target == null) {
            Log.e(TAG, "No target view found for level " + level);
            return;
        }

        target.post(() -> {
            if (avatar == null) {
                Log.e(TAG, "Avatar view is null, cannot animate.");
                return;
            }
            
            float targetX = target.getX() - (avatar.getWidth() / 2f);
            float targetY = target.getY()- avatar.getHeight();

            ObjectAnimator animX = ObjectAnimator.ofFloat(avatar, "x", targetX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(avatar, "y", targetY);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(animX, animY);
            set.setDuration(600);
            set.start();
        });
    }
}