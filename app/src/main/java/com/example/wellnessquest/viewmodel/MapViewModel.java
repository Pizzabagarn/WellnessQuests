package com.example.wellnessquest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.model.User;

public class MapViewModel extends ViewModel {

    private final UserViewModel userViewModel;
    private final MediatorLiveData<Integer> currentLevel;
    private final MediatorLiveData<Integer> coins;

    public MapViewModel(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;

        currentLevel = new MediatorLiveData<>();
        coins = new MediatorLiveData<>();

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

        Log.d("MapViewModel", "currLevel=" + currLevel + " coins=" + coinBalance + " cost=" + cost + " levelNumber=" + levelNumber);

        return currLevel != null && coinBalance != null &&
                levelNumber == currLevel + 1 && coinBalance >= cost;
    }
}
