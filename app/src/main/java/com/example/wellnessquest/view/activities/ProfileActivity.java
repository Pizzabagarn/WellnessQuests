package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.view.fragments.ProfileFragment;
import com.example.wellnessquest.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends BaseDrawerActivity {

    private EditText inputName, inputAge, inputPurpose;
    private Button saveButton;
    private ProgressBar progressBar;
    private ProfileViewModel viewModel;
    private String userId;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_profile, drawerBinding.contentFrame, true);


        Log.d("ProfileActivity", "Fragment transaction starting");

        profileFragment = new ProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.avatar_fragment_container, profileFragment)
                .commit();

        inputName = findViewById(R.id.input_name);
        inputAge = findViewById(R.id.input_age);
        inputPurpose = findViewById(R.id.input_goal);
        saveButton = findViewById(R.id.saveButton);
        progressBar = findViewById(R.id.profile_progress_bar);


        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userId = currentUser.getUid();

        // 🔄 OBSERVER: uppdaterar fält varje gång user LiveData uppdateras
        viewModel.getUserProfile(userId).observe(this, user -> {
            if (user != null) {
                inputName.setText(user.getName());
                inputAge.setText(String.valueOf(user.getAge()));
                inputPurpose.setText(user.getPurpose());

                 //skicka sparad avatar till fragmentet
                if (user.getAvatar() != null) {
                    profileFragment.setSelectedAvatar(user.getAvatar());
                }
            }
        });

        viewModel.getIsLoading().observe(this, isLoading ->
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );

        viewModel.getSaveStatus().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );

        saveButton.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String ageStr = inputAge.getText().toString().trim();
            String purpose = inputPurpose.getText().toString().trim();

            int age;
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Enter a valid age", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty() || purpose.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedAvatar = profileFragment != null ? profileFragment.getSelectedAvatar() : null;

            User user = new User();
            user.setUid(userId);
            user.setName(name);
            user.setAge(age);
            user.setPurpose(purpose);
            user.setAvatar(selectedAvatar);

            viewModel.saveUser(userId, user);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserProfile(userId); // 🔁 Ladda ny data från Firestore
    }
}
