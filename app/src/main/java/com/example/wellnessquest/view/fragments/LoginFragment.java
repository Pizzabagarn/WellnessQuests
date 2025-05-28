package com.example.wellnessquest.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.databinding.FragmentLoginBinding;
import com.example.wellnessquest.view.activities.HomeActivity;
import com.example.wellnessquest.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.example.wellnessquest.viewmodel.ProfileViewModel;
import com.example.wellnessquest.model.UserManager;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Go to Register screen
        binding.registerLink.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Show Toast messages
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                viewModel.clearToast();
            }
        });

        // Navigate to HomeActivity on successful login /// Changed this code so that name, age and purpose from the profile are saved anbd fetched correctly
        viewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null && firebaseUser.getEmail() != null) {
                    String userId = firebaseUser.getUid();

                    // Load profile in background, no need to wait
                    ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
                    profileViewModel.getUserProfile(userId).observe(getViewLifecycleOwner(), user -> {
                        if (user != null) {
                            UserManager.getInstance().setCurrentUser(user);
                        }
                    });
                }

                // ✅ Navigate immediately after login success
                startActivity(new Intent(requireContext(), HomeActivity.class));
                requireActivity().finish();
                viewModel.resetLoginSuccess();
            }
        });


// until here

        return binding.getRoot();
    }
}
