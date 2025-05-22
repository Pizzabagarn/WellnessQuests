package com.example.wellnessquest.model;

import com.example.wellnessquest.model.Quest;

import java.util.List;

public class Level {
    private int levelNumber;
    private int unlockCost;

    private List<Quest> quests;

    public Level(int levelNumber, int unlockCost, List<Quest> quests) {
        this.levelNumber = levelNumber;
        this.unlockCost = unlockCost;
        this.quests = quests;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getUnlockCost() {
        return unlockCost;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public int getCost() {
        return unlockCost;
    }

}
