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
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.view.activities.HomeActivity;
import com.example.wellnessquest.viewmodel.LoginViewModel;

/**
 * LoginFragment handles the user login UI and logic.
 * It uses data binding to connect the layout with the LoginViewModel,
 * navigates to the register screen, and responds to login events.
 *
 * @author Alexander Westman
 */
public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public LoginFragment() {
    }

    /**
     * Inflates the layout and initializes ViewModel bindings and observers.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The root view for the fragment's UI
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Navigate to Register screen when register link is clicked
        binding.registerLink.setOnClickListener(v -> {
            SoundManager.getInstance(requireContext()).playButtonClick();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Observe toast messages and show them
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                viewModel.clearToast();
            }
        });

        // Observe login success and navigate to HomeActivity
        viewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                startActivity(new Intent(requireContext(), HomeActivity.class));
                requireActivity().finish();
                viewModel.resetLoginSuccess();
            }
        });

        return binding.getRoot();
    }
}
