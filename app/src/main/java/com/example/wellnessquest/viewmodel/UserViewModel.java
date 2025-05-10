package com.example.wellnessquest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.wellnessquest.model.Quest;
import com.example.wellnessquest.model.User;
import com.example.wellnessquest.model.UserManager;
import com.google.firebase.auth.FirebaseAuth;
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

    public void addCoins(int amount) {
        User user = userLiveData.getValue();
        if (user != null) {
            user.earnCoins(amount);
            userLiveData.setValue(user); // Trigger observer

            // Uppdatera i Firestore
            firestore.collection("users")
                    .document(user.getUid())
                    .set(user);
        }
    }
    public void completeQuest(Quest quest, String imageUrl, String description) {
        User user = userLiveData.getValue();
        if (user == null) return;

        user.getCompletedQuests().add(quest);
        user.earnCoins(quest.getRewardCoins());
        quest.setCompleted(true);

        firestore.collection("users").document(user.getUid()).set(user);

        // Spara till dagbok också (exempel)
        Map<String, Object> entry = new HashMap<>();
        entry.put("title", quest.getTitle());
        entry.put("description", description);
        entry.put("imageUrl", imageUrl);
        entry.put("timestamp", System.currentTimeMillis());

        firestore.collection("users")
                .document(user.getUid())
                .collection("diaryEntries")
                .add(entry);

        userLiveData.setValue(user); // Trigga observer
    }
}

