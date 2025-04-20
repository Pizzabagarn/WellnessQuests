package com.example.wellnessquest.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wellnessquest.R;
import com.example.wellnessquest.view.activities.HomeActivity;
import com.example.wellnessquest.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.example.wellnessquest.databinding.FragmentLoginBinding binding =
                com.example.wellnessquest.databinding.FragmentLoginBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Navigera till RegisterFragment
        binding.registerLink.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Observera Toast
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                viewModel.clearToast();
            }
        });

        viewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                startActivity(new Intent(requireContext(), HomeActivity.class));
                requireActivity().finish(); // stänger Login-aktiviteten
                viewModel.resetLoginSuccess(); // nollställer den loginSuccess
            }
        });

        return binding.getRoot();
    }
}
