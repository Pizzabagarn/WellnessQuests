package com.example.wellnessquest.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class User {
    private String email;
    private int coins;
    private int level = 1;
    private String uid; // to save document-id for firebase
    private List<Quest> currentQuests;
    private List<Quest> completedQuests;

    // Empty constructor for Firestore
    public User() {
    }

    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.currentQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public int getCoins() {
        return coins;
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
        this.level++;
    }

    public void completeQuest(String questId) {
        for (Quest q : currentQuests) {

            if (q.getId().equals(questId) && !q.isComplete()) {
                q.setComplete(true);
                earnCoins(q.getRewardCoins());
                advanceLevel();
                completedQuests.add(q);
                return;
            }
        }
    }

    /*
    public void completeQuest(String questId) {
        for (Quest q : currentQuests) {
            if (q.getId().equals(questId) && !q.isComplete()) {
                q.setComplete(true);
                earnCoins(q.getRewardCoins()); // lägg till coins
                advanceLevel(); // höj level
                completedQuests.add(q); // flytta till completed
                break;
            }
        }
    }

     */

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
