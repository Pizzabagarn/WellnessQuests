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

/**
 * ProfileActivity handles user profile input and interaction within the WellnessQuest app.
 * <p>
 * This activity allows the user to input their name, age, and wellness goals, and save the data
 * to Firebase via the ProfileViewModel. It also embeds a ProfileFragment to select and display an avatar.
 * Data is loaded and observed using LiveData and ViewModel architecture.
 * </p>
 *
 * <p>Extends {@link BaseDrawerActivity} to include navigation drawer functionality.</p>
 *
 * @author Gen Félix Teramoto
 */

public class ProfileActivity extends BaseDrawerActivity {

    private EditText inputName, inputAge, inputPurpose;
    private Button saveButton;
    private ProgressBar progressBar;
    private ProfileViewModel viewModel;
    private String userId;
    private ProfileFragment profileFragment;

    /**
     * Initializes the activity, sets up layout, bindings, ViewModel, and LiveData observers.
     * Inflates {@code activity_profile.xml} into the drawer layout.
     *
     * @param savedInstanceState Bundle containing saved instance state (if any)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_profile, drawerBinding.contentFrame, true);


        Log.d("ProfileActivity", "Fragment transaction starting");

        // Initialize and display avatar selection fragment
        profileFragment = new ProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.avatar_fragment_container, profileFragment)
                .commit();

        // Bind UI components
        inputName = findViewById(R.id.input_name);
        inputAge = findViewById(R.id.input_age);
        inputPurpose = findViewById(R.id.input_goal);
        saveButton = findViewById(R.id.saveButton);
        progressBar = findViewById(R.id.profile_progress_bar);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Check for authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userId = currentUser.getUid();

        // Observe profile data and update UI accordingly
        viewModel.getUserProfile(userId).observe(this, user -> {
            if (user != null) {
                inputName.setText(user.getName());
                inputAge.setText(String.valueOf(user.getAge()));
                inputPurpose.setText(user.getPurpose());

                // Send avatar selection to fragment
                if (user.getAvatar() != null) {
                    profileFragment.setSelectedAvatar(user.getAvatar());
                }
            }
        });

        // Show/hide progress bar based on loading state
        viewModel.getIsLoading().observe(this, isLoading ->
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );

        // Display result of save operation
        viewModel.getSaveStatus().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );

        // Display result of save operation
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

    /**
     * Called when the activity resumes.
     * Re-fetches the user profile to ensure the UI is updated with the latest data.
     */
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserProfile(userId); // Reload user profile from Firestore
    }
}
