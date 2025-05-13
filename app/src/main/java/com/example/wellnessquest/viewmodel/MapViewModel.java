package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {

    private final List<Level> levels = new ArrayList<>();
    private final MutableLiveData<Integer> currentLevel = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> coins = new MutableLiveData<>(500);

    public MapViewModel() {
        initLevels();
    }

    private void initLevels() {
        levels.add(new Level(0, 0));
        levels.add(new Level(1, 100));
        levels.add(new Level(2, 200));
        levels.add(new Level(3, 300));
        levels.add(new Level(4, 350));
        levels.add(new Level(5, 450));
        levels.add(new Level(6, 600));
        levels.add(new Level(7, 700));
        levels.add(new Level(8, 800));
        levels.add(new Level(9, 900));
        levels.add(new Level(10, 1000));
    }

    public LiveData<Integer> getCurrentLevel() {
        return currentLevel;
    }

    public LiveData<Integer> getCoins() {
        return coins;
    }

    public int getCostForLevel(int levelNumber) {
        for (Level l : levels) {
            if (l.getLevelNumber() == levelNumber) return l.getCost();
        }
        return -1;
    }

    public boolean canPurchaseLevel(int level) {
        int nextLevel = currentLevel.getValue() + 1;
        return level == nextLevel && coins.getValue() >= getCostForLevel(level);
    }

    public boolean purchaseLevel(int level) {
        if (!canPurchaseLevel(level)) return false;
        int cost = getCostForLevel(level);
        coins.setValue(coins.getValue() - cost);
        currentLevel.setValue(level);
        return true;
    }
}
