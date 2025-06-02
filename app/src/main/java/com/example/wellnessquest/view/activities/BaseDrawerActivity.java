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

public abstract class BaseDrawerActivity extends AppCompatActivity {

    protected LayoutDrawerBinding drawerBinding;
    protected UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🧩 Databinding för layout_drawer.xml
        drawerBinding = LayoutDrawerBinding.inflate(getLayoutInflater());
        setContentView(drawerBinding.getRoot());

        // 🧠 ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        drawerBinding.setUserViewModel(userViewModel);
        drawerBinding.setLifecycleOwner(this);

        // 🔐 Ladda användardata från Firestore
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userViewModel.loadUser(uid);

        // 🧭 Toolbar + Navigation Drawer
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

    @Override
    public void onBackPressed() {
        if (drawerBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
