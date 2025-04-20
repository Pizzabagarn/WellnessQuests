package com.example.wellnessquest.model;

public class UserManager {

    private static UserManager instance;
    private User currentUser;

    // Private constructor for singleton pattern
    private UserManager() {
    }

    // Get the singleton instance
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Set the current logged-in user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Get the current logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Clear user data (e.g., on logout)
    public void clear() {
        currentUser = null;
    }

    // Check if a user is currently logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
