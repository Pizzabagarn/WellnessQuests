package com.example.wellnessquest.view.activities;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.viewmodel.ProfileViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.wellnessquest.model.UserManager;



public class ProfileActivity extends AppCompatActivity {

    private User currentUserProfile;
    private EditText inputName, inputAge, inputPurpose;
    private Button saveButton;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inputName = findViewById(R.id.input_name);
        inputAge = findViewById(R.id.input_age);
        inputPurpose = findViewById(R.id.input_goal);
        saveButton = findViewById(R.id.saveButton);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        ProgressBar progressBar = findViewById(R.id.profile_progress_bar);

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // or redirect to login screen
            return;
        }
        String userId = currentUser.getUid();
        viewModel.getUserProfile(userId).observe(this, user -> {
            if (user != null) {
                // ← keep for display
                inputName.setText(user.getName());
                inputAge.setText(String.valueOf(user.getAge()));
                inputPurpose.setText(user.getPurpose());

                // ← save for later updates
                currentUserProfile = user;
            }
        });

        viewModel.getSaveStatus().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );

        viewModel.getIsLoading().observe(this, isLoading -> {
            // You can show/hide a ProgressBar here if needed

        });

        saveButton.setOnClickListener(v -> {
            String name    = inputName.getText().toString().trim();
            String ageStr  = inputAge.getText().toString().trim();
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

            // ← Make sure we have the user loaded
            if (currentUserProfile == null) {
                Toast.makeText(this, "Profile still loading…", Toast.LENGTH_SHORT).show();
                return;
            }

            // ← Update the in-memory User
            currentUserProfile.setName(name);
            currentUserProfile.setAge(age);
            currentUserProfile.setPurpose(purpose);

            // ← Push the save out
            viewModel.saveUser(userId, currentUserProfile);
            UserManager.getInstance().setCurrentUser(currentUserProfile);
            // The ViewModel’s saveStatus observer will toast the result for us
        });
    }
}
