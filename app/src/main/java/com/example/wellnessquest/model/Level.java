package com.example.wellnessquest.model;

import com.example.wellnessquest.model.Quest;

import java.util.List;

/**
 * Represents a level in the WellnessQuest application.
 * Each level has a number, a coin cost to unlock, and a list of quests.
 *
 * @author Alexander Westman
 */
public class Level {
    /**
     * Represents what level the user is currently on.
     */
    private int levelNumber;

    /**
     * Cost in coins to unlock a level.
     */
    private int unlockCost;

    /**
     * Every level has a list of quests.
     */
    private List<Quest> quests;

    /**
     * Constructs a new Level with the specified level number, unlock cost, and quests.
     *
     * @param levelNumber the number of the level
     * @param unlockCost the cost in coins to unlock this level
     * @param quests the list of quests for this level
     */
    public Level(int levelNumber, int unlockCost, List<Quest> quests) {
        this.levelNumber = levelNumber;
        this.unlockCost = unlockCost;
        this.quests = quests;
    }

    /**
     * Gets the level number.
     *
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Gets the unlock cost in coins.
     *
     * @return the unlock cost
     */
    public int getUnlockCost() {
        return unlockCost;
    }

    /**
     * Gets the list of quests associated with this level.
     *
     * @return the list of quests
     */
    public List<Quest> getQuests() {
        return quests;
    }
}
