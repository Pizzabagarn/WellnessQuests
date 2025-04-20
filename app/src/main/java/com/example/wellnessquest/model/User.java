package com.example.wellnessquest.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private int coins;
    private int currentLevel;
    private List<Quest> currentQuests;
    private List<Quest> completedQuests;

    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.currentLevel = 1;
        this.currentQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    // 🔧 Tom konstruktor krävs av Firestore
    public User() {
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public int getCoins() {
        return coins;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public List<Quest> getCurrentQuests() {
        return currentQuests;
    }

    public List<Quest> getCompletedQuests() {
        return completedQuests;
    }

    // Setters

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCurrentQuests(List<Quest> currentQuests) {
        this.currentQuests = currentQuests;
    }

    public void setCompletedQuests(List<Quest> completedQuests) {
        this.completedQuests = completedQuests;
    }

    // Utility
    public void earnCoins(int amount) {
        this.coins += amount;
    }

    public void advanceLevel() {
        this.currentLevel++;
    }
}
