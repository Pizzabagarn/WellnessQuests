package com.example.wellnessquest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.QuestRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
/**
 * UserViewModel manages user-related data and logic for the app.
 * It serves as a bridge between the UI and Firebase Firestore, handling user progress,
 * coin balance, level unlocking, and quest completion.
 */

public class UserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    /**
     * Constructor for UserViewModel.
     * @param application The application context.
     */
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Returns the observable user data.
     * @return LiveData representing the current user.
     */
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    /**
     * Sets the current user object.
     * @param user The user to be set.
     */
    public void setUser(User user) {
        userLiveData.setValue(user);
    }

    /**
     * Loads user data from Firestore using the provided UID and updates LiveData.
     * @param uid Firebase Authentication user ID.
     * @author Lowisa Svensson Christell
     * @author Alexander Westman
     */
    public void loadUser(String uid) {
        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User user = snapshot.toObject(User.class);
                        userLiveData.setValue(user);
                    } else {
                        Log.w("UserViewModel", "User not found in the database for UID: " + uid);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("UserViewModel", "Failed to get user from Firestore", e);
                });
    }

    /**
     * Unlocks the given level for the user by deducting coins, updating the current level,
     * and saving the user state to Firestore.
     * @param level The level to purchase.
     * @author Lowisa Svensson Christell
     */
    public void purchaseLevel(int level) {
        User user = userLiveData.getValue();
        if (user == null) return;

        int cost = getLevelCost(level);
        user.withdrawCoins(cost);
        user.setCurrentLevel(level);
        user.getUnlockedLevels().add(level);

        saveToFirestore(user);
        userLiveData.setValue(user);
    }

    /**
     * Returns the unlock cost of the specified level.
     * @param level The level number.
     * @return The cost to unlock the level.
     */
    public int getLevelCost(int level) {
        return QuestRepository.getLevel(level).getUnlockCost();
    }

    /**
     * Checks if the user has enough coins to purchase the specified level.
     * @param level The level to check.
     * @return True if the user can afford the level, false otherwise.
     * @author Lowisa Svensson Christell
     */
    public boolean canPurchaseLevel(int level){
        User user = userLiveData.getValue();
        if (user == null) return false;
        int cost = getLevelCost(level);
        int coins = user.getCoins();
        return coins >= cost;
    }

    /**
     * Checks whether the given level is the next level for the user.
     * @param level The level to check.
     * @return True if the level is the immediate next level, false otherwise.
     * @author Lowisa Svensson Christell
     */
    public boolean isNextLevel(int level) {
        User user = userLiveData.getValue();
        if (user == null) return false;
        return level == user.getCurrentLevel() + 1;
    }

    /**
     * Marks a quest as completed, adds it to the user's completed quests,
     * rewards coins, saves a diary entry, and updates Firestore.
     * @param quest       The completed quest.
     * @param imageUrl    Image to associate with the diary entry.
     * @param description Description of the quest result.
     * @author Alexander Westman
     */
    public void completeQuest(Quest quest, String imageUrl, String description) {
        User user = userLiveData.getValue();
        if (user == null) return;

        user.getCompletedQuests().add(quest.getId());
        user.earnCoins(quest.getRewardCoins());
        quest.setCompleted(true);

        saveToFirestore(user);

        Map<String, Object> entry = new HashMap<>();
        entry.put("title", quest.getTitle());
        entry.put("description", description);
        entry.put("imageUrl", imageUrl);
        entry.put("timestamp", System.currentTimeMillis());

        firestore.collection("users")
                .document(user.getUid())
                .collection("diaryEntries")
                .add(entry);

        userLiveData.setValue(user);
    }
    /**
     * Saves the user object to Firestore.
     * @param user The user to save.
     * @author Alexander Westman
     */
    private void saveToFirestore(User user) {
        firestore.collection("users")
                .document(user.getUid())
                .set(user);
    }
}
