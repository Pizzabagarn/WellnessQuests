package com.example.wellnessquest.model;

/**
 * Singleton class that manages the currently logged-in user in the WellnessQuest application.
 * This class provides global access to the current user object and handles login state.
 *
 * <p>Usage of the singleton pattern ensures there is only one instance of UserManager
 * throughout the app lifecycle.</p>
 *
 * @author Alexander Westman
 */
public class UserManager {

    /**
     * The singleton instance of the UserManager.
     */
    private static UserManager instance;

    /**
     * The currently logged-in user.
     */
    private User currentUser;

    /**
     * Private constructor to prevent external instantiation (singleton pattern).
     */
    private UserManager() {
    }

    /**
     * Returns the singleton instance of the UserManager.
     *
     * @return the singleton instance
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param user the user to set as the current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the current user, or {@code null} if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears the current user data, typically called during logout.
     */
    public void clear() {
        currentUser = null;
    }

    /**
     * Checks whether a user is currently logged in.
     *
     * @return {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
