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
import com.example.wellnessquest.viewmodel.LoginViewModel;

public class RegisterFragment extends Fragment {

    private LoginViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Databinding
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(inflater, container, false);

        // Hämta ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        // Koppla databinding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Observera Toast-meddelanden
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Navigera tillbaka till login efter lyckad registrering
        viewModel.getNavigateToLogin().observe(getViewLifecycleOwner(), navigate -> {
            if (navigate) {
                requireActivity().getSupportFragmentManager().popBackStack();
                viewModel.resetNavigation();
            }
        });

        return binding.getRoot();
    }
}
