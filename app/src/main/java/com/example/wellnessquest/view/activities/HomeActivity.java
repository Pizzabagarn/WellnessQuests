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
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends BaseDrawerActivity {

    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ViewModel
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        // Hämta nuvarande användare från UserManager
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }

        // Toolbar och drawer setup
        setSupportActionBar(binding.toolbar);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation item clicks
        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                showToast("Home selected");

            } else if (id == R.id.nav_quests) {
                startActivity(new Intent(this, QuestActivity.class));

            } else if (id == R.id.nav_map) {
                startActivity(new Intent(this, MapActivity.class));

            } else if (id == R.id.nav_profile) {
                showToast("Profile selected");

            } else if (id == R.id.nav_logout) {
                // 🔐 Logga ut från Firebase
                FirebaseAuth.getInstance().signOut();

                // 🧼 Rensa lokal data om det behövs
                getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();

                // 🔁 Gå tillbaka till inloggningen
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void showToast(String msg) {
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Uppdatera senaste aktivitet
        new UserStorage(getApplicationContext()).updateLastActive();

        // Uppdatera ViewModel med aktuell användare
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.setUser(user);
        }
    }
}
