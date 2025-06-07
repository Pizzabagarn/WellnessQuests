package com.example.wellnessquest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the WellnessQuest application.
 *
 * <p>A user has a profile (name, age, purpose, avatar), gameplay attributes (coins, level, completed quests),
 * and is uniquely identified by a UID which matches the Firebase Authentication ID.</p>
 *
 * <p>This class is used for saving and loading user data to/from Firebase Firestore.</p>
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
     * Constructs a new User with the specified email address.
     * Other user data is initialized with default values.
     *
     * @param email the user's email address
     */
    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.currentLevel = 0;
        this.completedQuests = new ArrayList<>();
        this.name = null;
        this.age = 0;
        this.purpose = null;
    }

    /**
     * No-argument constructor required for Firebase deserialization.
     */
    public User() {
    }

    // ============================
    //         Getters
    // ============================

    /**
     * Returns the user's email.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's current number of coins.
     *
     * @return the user's coin count
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Returns the user's current level.
     *
     * @return the user's current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the list of unlocked level indices.
     *
     * @return a list of unlocked levels
     */
    public List<Integer> getUnlockedLevels() {
        return unlockedLevels;
    }

    /**
     * Returns the list of quest identifiers that the user has completed.
     *
     * @return a list of completed quest IDs
     */
    public List<String> getCompletedQuests() {
        return completedQuests;
    }

    /**
     * Returns the user's unique Firebase ID.
     *
     * @return the Firebase UID
     */
    public String getUid() {
        return uid;
    }

    /**
     * Returns the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user's age.
     *
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the user's wellness purpose or goal.
     *
     * @return the user's purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Returns the avatar selected by the user.
     *
     * @return the avatar image or identifier
     */
    public String getAvatar() {
        return avatar;
    }

    // ============================
    //         Setters
    // ============================

    /**
     * Sets the number of coins the user has.
     *
     * @param coins the coin count to set
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Sets the user's current level.
     *
     * @param currentLevel the level to set
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Sets the list of completed quests.
     *
     * @param completedQuests a list of completed quest IDs
     */
    public void setCompletedQuests(List<String> completedQuests) {
        this.completedQuests = completedQuests;
    }

    /**
     * Sets the Firebase UID of the user.
     *
     * @param uid the Firebase user ID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Sets the user's name.
     *
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the user's age.
     *
     * @param age the user's age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the user's purpose or wellness goal.
     *
     * @param goal the user's purpose
     */
    public void setPurpose(String goal) {
        this.purpose = goal;
    }

    /**
     * Sets the user's avatar.
     *
     * @param avatar the avatar image or ID
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // ============================
    //         Utility
    // ============================

    /**
     * Increases the user's coin count by a specified amount.
     *
     * @param amount the number of coins to add
     */
    public void earnCoins(int amount) {
        this.coins += amount;
    }

    /**
     * Decreases the user's coin count by a specified amount.
     *
     * @param amount the number of coins to subtract
     */
    public void withdrawCoins(int amount) {
        this.coins -= amount;
    }

    /**
     * Advances the user to the next level by incrementing their current level.
     */
    public void advanceLevel() {
        this.currentLevel++;
    }
}
