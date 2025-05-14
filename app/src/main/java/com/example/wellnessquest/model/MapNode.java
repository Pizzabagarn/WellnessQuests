package com.example.wellnessquest.model;

public class MapNode {
    private int level;
    private int requiredCoins;
    private boolean isUnlocked;

    public MapNode(int level, int requiredCoins, boolean isUnlocked){
        this.level = level;
        this.requiredCoins = requiredCoins;
        this.isUnlocked = isUnlocked;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRequiredCoins() {
        return requiredCoins;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked){
        isUnlocked = unlocked;
    }
}
