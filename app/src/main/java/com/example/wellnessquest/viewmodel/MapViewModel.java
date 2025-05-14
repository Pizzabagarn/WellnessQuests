package com.example.wellnessquest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wellnessquest.model.Level;
import com.example.wellnessquest.model.MapNode;
import com.example.wellnessquest.model.QuestRepository;
import com.example.wellnessquest.model.User;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {

    private final MediatorLiveData<List<MapNode>> mapNodes = new MediatorLiveData<>();

    public MapViewModel(UserViewModel userViewModel) {
        // Observera userLiveData och uppdatera noder varje gång användaren ändras
        mapNodes.addSource(userViewModel.getUserLiveData(), user -> {
            if (user != null) {
                mapNodes.setValue(generateMapNodes(user.getCurrentLevel()));
            }
        });
    }

    public LiveData<List<MapNode>> getMapNodes() {
        return mapNodes;
    }

    private List<MapNode> generateMapNodes(int currentLevel) {
        List<MapNode> nodes = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Level level = QuestRepository.getLevel(i);
            boolean isUnlocked = i <= currentLevel;
            nodes.add(new MapNode(i, level.getUnlockCost(), isUnlocked));
        }
        return nodes;
    }
}
