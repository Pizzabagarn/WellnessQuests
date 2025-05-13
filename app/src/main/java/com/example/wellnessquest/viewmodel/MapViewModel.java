package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.QuestRepository;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {

    private final List<Level> levels;

    public MapViewModel() {
        this.levels = QuestRepository.getAllLevels();
    }

    public Level getLevel(int levelNumber) {
        for (Level l : levels) {
            if (l.getLevelNumber() == levelNumber) return l;
        }
        return null;
    }
}
