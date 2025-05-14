package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityMapBinding;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.viewmodel.UserViewModel;

public class MapActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private ActivityMapBinding binding;
    private int currentNode = 0;  // Håller koll på den aktuella noden som avataren är på.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

      //  binding.setLifecycleOwner(this);
        binding.setUserViewModel(userViewModel);


        // Hämta referenser till UI-elementen
        binding.avatar.setVisibility(View.VISIBLE);


        // Klickhantering för noder (klickbar noder)
        binding.node0.setOnClickListener(v -> onNodeClicked(0));
        binding.node1.setOnClickListener(v -> onNodeClicked(1));
        binding.node2.setOnClickListener(v -> onNodeClicked(2));
        binding.node3.setOnClickListener(v -> onNodeClicked(3));
        // Lägg till fler noder här med samma pattern
    }

    // Funktion som hanterar när en användare klickar på en nod
    private void onNodeClicked(int nodeId) {
        boolean unlocked = userViewModel.unlockNextLevelIfAffordable();

        if (unlocked){
            animateAvatarToNode(nodeId);
        } else {
            showErrorMessage("Level locked!");
        }
    }


    // Funktion som animerar avataren till en specifik nod
    private void animateAvatarToNode(int nodeId) {
        // Här gör vi en enkel animation av avataren till den valda noden
        int targetX = 0, targetY = 0;

        // Sätt X- och Y-koordinater baserat på nodens position
        switch (nodeId) {
            case 0:
                targetX = (int) binding.gx0.getX();
                targetY = (int) binding.gy0.getY();
                break;
            case 1:
                targetX = (int) binding.gx1.getX();
                targetY = (int) binding.gy1.getY();
                break;
            case 2:
                targetX = (int) binding.gx2.getX();
                targetY = (int) binding.gy2.getY();
                break;
            // Fler fall för andra noder
        }

        // Använd en enkel animation till den nya positionen
        binding.avatar.animate()
                .x(targetX)
                .y(targetY)
                .setDuration(300)
                .start();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
