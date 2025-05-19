package com.example.wellnessquest.model;

import java.util.ArrayList;
import java.util.List;


public class User {
    private String email;
    private int coins;
    private int currentLevel;
    private String uid; // to save document-id for firebase
    private List<String> completedQuests;
    private String name; // Profile
    private int age; // Profile
    private String purpose; // Profile


    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.currentLevel = 1;
        this.completedQuests = new ArrayList<>();
        this.name = name;
        this.age = age;
        this.purpose = purpose;

    }

    // 🔧 Empty constructor for Firestore
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

    public List<String> getCompletedQuests() {
        return completedQuests;
    }

    // Setters

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setCompletedQuests(List<String> completedQuests) {
        this.completedQuests = completedQuests;
    }

    // Utility
    public void earnCoins(int amount) {
        this.coins += amount;
    }

    public void advanceLevel() {
        this.currentLevel++;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPurpose() { return purpose; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setPurpose(String goal) { this.purpose = goal; }
}
