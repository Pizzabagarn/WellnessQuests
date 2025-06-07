package com.example.wellnessquest.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.databinding.FragmentRegisterBinding;
import com.example.wellnessquest.utils.SoundManager;
import com.example.wellnessquest.viewmodel.LoginViewModel;

/**
 * RegisterFragment handles user registration and communicates with LoginViewModel.
 * It observes changes to LiveData for feedback and navigation events.
 *
 * @author Alexander Westman
 */
public class RegisterFragment extends Fragment {

    private LoginViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public RegisterFragment() {
    }

    /**
     * Inflates the layout for the fragment, sets up databinding and observers for UI updates.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The root view for the fragment's UI
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Observe toast messages to display feedback to the user
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                viewModel.clearToast();
            }
        });

        // Observe registration success to navigate back to the login screen
        viewModel.getNavigateToLogin().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate != null && navigate) {
                SoundManager.getInstance(requireContext()).playButtonClick();
                requireActivity().getSupportFragmentManager().popBackStack();
                viewModel.resetNavigation();
            }
        });

        return binding.getRoot();
    }
}
