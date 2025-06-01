package com.example.wellnessquest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.ActivityHomeBinding;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.example.wellnessquest.model.UserStorage;
import com.example.wellnessquest.view.fragments.HomeFragment;
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🧩 Lägg in Home-layouten i content_frame från BaseDrawerActivity
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        drawerBinding.contentFrame.addView(binding.getRoot());

        // 🔥 Ladda in HomeFragment i fragment_container - Gen
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        // ViewModel
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        // 🔄 Sätt användare
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }

        // Navigation click listener
        drawerBinding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                showToast("Already in home page");
            } else if (id == R.id.nav_quests) {
                SoundManager.getInstance(HomeActivity.this).playQuestSound();
                startActivity(new Intent(this, QuestActivity.class));
            } else if (id == R.id.nav_map) {
                SoundManager.getInstance(HomeActivity.this).playButtonClick();
                startActivity(new Intent(this, MapActivity.class));
            } else if (id == R.id.nav_profile) {
                SoundManager.getInstance(HomeActivity.this).playButtonClick();
                startActivity(new Intent(this, ProfileActivity.class));

            } else if (id == R.id.nav_logout) {
                SoundManager.getInstance(HomeActivity.this).playButtonClick();
                FirebaseAuth.getInstance().signOut();
                getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            drawerBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserStorage(getApplicationContext()).updateLastActive();

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User currentUser = UserManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            userViewModel.setUser(currentUser);
            userViewModel.loadUser(currentUser.getUid());
        }
    }

    private void showToast(String msg) {
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();
    }
}
