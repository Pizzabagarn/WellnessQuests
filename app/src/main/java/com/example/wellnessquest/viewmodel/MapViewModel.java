package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.QuestRepository;

import java.util.List;

public class MapViewModel extends ViewModel {

    public Level getLevel(int levelNumber) {
        return QuestRepository.getLevel(levelNumber);
    }

    public List<Level> getAllLevels() {
        return QuestRepository.getAllLevels();
    }
}