package com.example.wellnessquest.model;

import java.io.Serializable;
import java.util.List;

public class Quest implements Serializable {
    private String id;
    private String title;
    private String description;
    private String category; // "Fitness" eller "Mind"
    private boolean isCompleted;
    private int coins;
    private List<String> validTags;

    public Quest(String id, String title, String description, String category, boolean isCompleted, int coins, List<String> validTags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.isCompleted = isCompleted;
        this.coins = coins;  //Döpa om till coinsReward?
        this.validTags = validTags;
    }

    // Getters
    public String getId() {
        return id;
    }

    public List<String> getValidTags() {
        return validTags;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getCoins() {
        return coins;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setValidTags(List<String> validTags) {
        this.validTags = validTags;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String setCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
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

    public void markComplete() {
        this.isCompleted = true;
    }
}