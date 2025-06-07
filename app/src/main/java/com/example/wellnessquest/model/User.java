package com.example.wellnessquest.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a user in the WellnessQuest application.
 * <p>
 * A user has a profile (name, age, purpose, avatar), gameplay attributes (coins, level, completed quests),
 * and is uniquely identified by a UID which matches the Firebase Authentication ID.
 * </p>
 * <p>
 * This class is used for saving and loading user data to/from Firebase Firestore.
 * </p>
 *
 * @author Gen Felíx Teramoto
 * @author Alexander Westman
 * @author Mena Nasir
 * @author Lowisa Svensson Christell
 */
public class User {
    private String email;
    private int coins;
    private int currentLevel;
    private String uid; // Firebase document ID
    private List<String> completedQuests;
    private String name;
    private int age;
    private String purpose;
    private String avatar;
    private List<Integer> unlockedLevels = new ArrayList<>();

    /**
     * Creates a new User with the specified email. Other fields are initialized with default values.
     *
     * @param email The user's email address
     */
    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.currentLevel = 0;
        this.completedQuests = new ArrayList<>();
        this.name = name;
        this.age = age;
        this.purpose = purpose;

    }

    /**
     * Empty constructor required for Firebase deserialization.
     */
    public User() {
    }

    // ============================
    //         Getters
    // ============================

    /**
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the number of coins the user currently has
     */
    public int getCoins() {
        return coins;
    }


    public void withdrawCoins (int amount) {this.coins -= amount;}


    public int getCurrentLevel() {
        return currentLevel;
    }


    public List<Integer> getUnlockedLevels() {
        return unlockedLevels;
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

    // Utility
    public void earnCoins(int amount) {
        this.coins += amount;
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


    public String getAvatar() { return avatar; }


    public void setAvatar(String avatar) { this.avatar = avatar; }

}
