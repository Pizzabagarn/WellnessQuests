package com.example.wellnessquest.model;

public class Quest {
    private String id;
    private String title;
    private String description;
    private String category; // "Fitness" eller "Mind"
    private boolean isCompleted;
    private int coins;
    private int requiredLevel;

    public Quest(String id, String title, String description, String category, boolean isCompleted, int coins, int requiredLevel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.isCompleted = isCompleted;
        this.coins = coins;
        this.requiredLevel = requiredLevel;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getCoins() {
        return coins;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Quest() {
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isComplete() {
        return isCompleted;
    }

    public int getRewardCoins() {
        return coins;
    }

    public void setComplete(boolean complete) {
        this.isCompleted = complete;
    }
}