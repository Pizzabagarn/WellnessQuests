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

public class UserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUser(User user) {
        userLiveData.setValue(user);
    }

    //Loads current user related data from firestore
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


    public void addCoins(int amount) {
        User user = userLiveData.getValue();
        if (user != null) {
            user.earnCoins(amount);
            userLiveData.setValue(user);
            saveToFirestore(user);
        }
    }

    //sets user-level and updates level in firestore + drar coins vid level köp
    // lägger till upplåsta levels i lista
    public void purchaseLevel(int level) {
        User user = userLiveData.getValue();
        if (user == null) return;

        int cost = getLevelCost(level); //drar coins när level ändras,
        user.withdrawCoins(cost);
        user.setCurrentLevel(level);
        user.getUnlockedLevels().add(level);

        saveToFirestore(user);
        userLiveData.setValue(user);
    }

    public int getLevelCost(int level) {
        return QuestRepository.getLevel(level).getUnlockCost();
    }

    public boolean canPurchaseLevel(int level){
        User user = userLiveData.getValue();
        if (user == null) return false;
        int cost = getLevelCost(level);
        int coins = user.getCoins();
        return coins >= cost;
    }

    public boolean isNextLevel(int level) {
        User user = userLiveData.getValue();
        if (user == null) return false;
        return level == user.getCurrentLevel() + 1;
    }

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

    private void saveToFirestore(User user) {
        firestore.collection("users")
                .document(user.getUid())
                .set(user);
    }
}
