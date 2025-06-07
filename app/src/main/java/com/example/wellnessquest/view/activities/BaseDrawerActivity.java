package com.example.wellnessquest.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.LayoutDrawerBinding;
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.navigation.NavigationView;

/**
 * Abstract base class for all activities in WellnessQuest that use a navigation drawer layout.
 * <p>
 * Sets up the drawer, toolbar, user data binding and handles navigation between
 * major parts of the app such as Home, Quests, Map, Profile, and Logout.
 * </p>
 *
 * Activities like {@code HomeActivity}, {@code MapActivity}, {@code QuestActivity} and
 * {@code ProfileActivity} extend this class to inherit the common drawer UI.
 *
 * @author Mena Nasir
 */
public abstract class BaseDrawerActivity extends AppCompatActivity {

    /** View binding for the drawer layout */
    protected LayoutDrawerBinding drawerBinding;

    /** ViewModel holding user data such as coins and level */
    protected UserViewModel userViewModel;

    /**
     * Called when the activity is starting.
     * <p>
     * Initializes view binding, sets up the toolbar and drawer toggle, connects the
     * navigation menu to this class, and loads user data.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerBinding = LayoutDrawerBinding.inflate(getLayoutInflater());
        setContentView(drawerBinding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        drawerBinding.setUserViewModel(userViewModel);
        drawerBinding.setLifecycleOwner(this);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);


        setSupportActionBar(drawerBinding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerBinding.drawerLayout,
                drawerBinding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerBinding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    /**
     * Handles navigation drawer item selection.
     * Launches different activities based on the selected menu item
     * and plays relevant sound effects using {@link SoundManager}.
     *
     * @param item The selected menu item
     * @return true if the selection is handled
     */
    protected boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            SoundManager.getInstance(BaseDrawerActivity.this).playButtonClick();
            startActivity(new Intent(this, HomeActivity.class));
        } else if (id == R.id.nav_quests) {
            SoundManager.getInstance(BaseDrawerActivity.this).playQuestSound();
            startActivity(new Intent(this, QuestActivity.class));
        } else if (id == R.id.nav_map) {
            SoundManager.getInstance(BaseDrawerActivity.this).playButtonClick();
            startActivity(new Intent(this, MapActivity.class));
        } else if (id == R.id.nav_profile) {
            SoundManager.getInstance(BaseDrawerActivity.this).playButtonClick();
            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.nav_logout) {
            SoundManager.getInstance(BaseDrawerActivity.this).playButtonClick();
            FirebaseAuth.getInstance().signOut();
            getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        drawerBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Overrides the default back button behavior to close the drawer if it's open.
     * If the drawer is not open, proceeds with default behavior (finishes activity or goes back).
     */
    @Override
    public void onBackPressed() {
        if (drawerBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
