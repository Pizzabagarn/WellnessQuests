package com.example.wellnessquest.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MapViewModelFactory implements ViewModelProvider.Factory {
    private final UserViewModel userViewModel;

    public MapViewModelFactory(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(userViewModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
