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

/**
 * HomeActivity serves as the main landing screen after a user logs in.
 * <p>
 * It displays the {@link HomeFragment}, handles navigation drawer interaction,
 * and initializes the {@link UserViewModel} to sync user data with the UI.
 * </p>
 * <p>
 * This activity extends {@link BaseDrawerActivity}, allowing it to reuse
 * navigation drawer functionality and inject its layout into the base container.
 * </p>
 *
 * @author Gen Félix Teramoto
 * @author Alex Westman
 * @author Mena Nasir
 * @author Lowisa Svensson Christell
 */
public class HomeActivity extends BaseDrawerActivity {

    /**
     * Initializes the HomeActivity, sets up fragment loading, ViewModel binding,
     * navigation drawer listeners, and loads current user data.
     *
     * @param savedInstanceState The saved instance state from a previous instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject Home layout into the drawer's content frame
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        drawerBinding.contentFrame.addView(binding.getRoot());

        // Load HomeFragment into the fragment container
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        // Initialize UserViewModel and bind it to the layout
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setUserViewModel(userViewModel);
        binding.setLifecycleOwner(this);

        // Set the current user into the ViewModel
        User user = UserManager.getInstance().getCurrentUser();
        if (user != null) {
            userViewModel.setUser(user);
        }

        // Set up navigation menu click behavior
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

    /**
     * Called when the activity is resumed.
     * Updates the user's last active timestamp and reloads user data into the ViewModel.
     */
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

    /**
     * Displays a short toast message to the user.
     *
     * @param msg The message to display
     */
    private void showToast(String msg) {
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();
    }
}
