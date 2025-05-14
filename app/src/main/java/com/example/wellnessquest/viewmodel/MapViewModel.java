package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.model.User;

public class MapViewModel extends ViewModel {

    private final MediatorLiveData<Integer> currentLevel = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> coins = new MediatorLiveData<>();

    public MapViewModel(UserViewModel userViewModel) {
        // Lyssna på förändringar i användaren
        currentLevel.addSource(userViewModel.getUserLiveData(), user -> {
            if (user != null) currentLevel.setValue(user.getCurrentLevel());
        });
        coins.addSource(userViewModel.getUserLiveData(), user -> {
            if (user != null) coins.setValue(user.getCoins());
        });
    }

    public LiveData<Integer> getCurrentLevel() {
        return currentLevel;
    }

    public LiveData<Integer> getCoins() {
        return coins;
    }

    public int getCostForLevel(int levelNumber) {
        return QuestRepository.getLevel(levelNumber).getUnlockCost();
    }

    public boolean canPurchaseLevel(int levelNumber) {
        Integer currLevel = currentLevel.getValue();
        Integer coinBalance = coins.getValue();
        int cost = getCostForLevel(levelNumber);

        return currLevel != null && coinBalance != null &&
                levelNumber == currLevel + 1 && coinBalance >= cost;
    }
}
