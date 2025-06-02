package com.example.wellnessquest.model;

import com.example.wellnessquest.model.Quest;

import java.util.List;

public class Level {
    private int levelNumber; // Represent what level the user is currently on
    private int unlockCost; // Cost in coins to unlock a level
    private List<Quest> quests; // Every level has a list of quests

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
