package com.example.wellnessquest.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * UserStorage handles local persistence of user-related data using SharedPreferences.
 *
 * <p>This class was originally intended as a lightweight alternative to a full backend database,
 * allowing the app to store, retrieve, and manage {@link User} objects locally during early development.
 * While Firebase Firestore is now the primary storage backend, UserStorage continues to be used for:
 *
 * <ul>
 *   <li>Session management (e.g. saving and clearing the current user)</li>
 *   <li>Tracking last active timestamps for auto logout</li>
 *   <li>Development and testing environments without Firebase</li>
 * </ul>
 *
 * <p>Each user is stored as a JSON string keyed by their username. Passwords are securely hashed
 * using SHA-256 before storage. The currently logged-in user is tracked separately.
 *
 * <p>Usage example:
 * <pre>{@code
 *     UserStorage storage = new UserStorage(context);
 *     User user = storage.getCurrentUser();
 *     if (user != null) {
 *         // Use current user
 *     }
 * }</pre>
 *
 * @author Alexander Westman
 */

public class UserStorage {

    private static final String PREF_NAME = "UserPrefs";
    private static final String USER_PREFIX = "user_";
    private static final String CURRENT_USER_KEY = "current_user";
    private static final String LAST_ACTIVE_KEY = "last_active";


    private SharedPreferences sharedPreferences;
    private Gson gson;

    public UserStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public boolean userExists(String username) {
        return sharedPreferences.contains(USER_PREFIX + username);
    }

    public User findUserByUsername(String username) {
        String json = sharedPreferences.getString(USER_PREFIX + username, null);
        if (json != null) {
            return gson.fromJson(json, User.class);
        }
        return null;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getKey().startsWith(USER_PREFIX)) {
                users.add(gson.fromJson(entry.getValue().toString(), User.class));
            }
        }
        return users;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hash, Base64.NO_WRAP); // lagrar som String
        } catch (Exception e) {
            e.printStackTrace();
            return password; // fallback
        }
    }

    public void saveCurrentUser(User user) {
        String json = gson.toJson(user);
        sharedPreferences.edit()
                .putString(CURRENT_USER_KEY, json)
                .putLong(LAST_ACTIVE_KEY, System.currentTimeMillis()) // 🕒 Spara tidpunkt
                .apply();
    }

    public User getCurrentUser() {
        String json = sharedPreferences.getString(CURRENT_USER_KEY, null);
        if (json != null) {
            long lastActive = sharedPreferences.getLong("last_active", 0);
            long now = System.currentTimeMillis();

            if (now - lastActive > 15 * 60 * 1000) { // 15 minuter
                clearCurrentUser(); // logga ut
                return null;
            }

            return gson.fromJson(json, User.class);
        }
        return null;
    }

    public void clearCurrentUser() {
        sharedPreferences.edit()
                .remove(CURRENT_USER_KEY)
                .remove(LAST_ACTIVE_KEY)
                .apply();
    }

    public void updateLastActive() {
        sharedPreferences.edit()
                .putLong(LAST_ACTIVE_KEY, System.currentTimeMillis())
                .apply();
    }

}
